package de.kct.kct.service;

import de.kct.kct.dto.GenerateIlvDto;
import de.kct.kct.entity.Artikelstammdaten;
import de.kct.kct.entity.Datensatz;
import de.kct.kct.entity.User;
import de.kct.kct.entity.UserKostenstelle;
import de.kct.kct.repository.ArtikelstammdatenRepository;
import de.kct.kct.repository.DatensatzRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ArtikelstammdatenService {
    private ArtikelstammdatenRepository artikelstammdatenRepository;
    private ExcelUploadService excelUploadService;
    private DatensatzRepository datensatzRepository;

    public void saveData(MultipartFile file) {
        if (excelUploadService.isValidExcelFile(file)) {
            try {
                List<Artikelstammdaten> artikelstammdatenList = excelUploadService.getArtikelstammdatenFromExcel(file.getInputStream());
                this.artikelstammdatenRepository.saveAll(artikelstammdatenList);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }

    public void getAll() {
        List<Artikelstammdaten> artikelstammdatenList = artikelstammdatenRepository.findAll();
    }

    public byte[] generateIlv(User currentUser, GenerateIlvDto generateIlvDto) {
        int year = LocalDate.now().getYear();
        List<String> userKostenstellen = currentUser.getKostenstellen().stream().map(UserKostenstelle::getKostenstelle).toList();
        List<Datensatz> datensatzList = datensatzRepository.findDatensaetzeForKostenstellenAndMonth(userKostenstellen, generateIlvDto.monat(), year);
        List<String> oekurzList = datensatzList.stream().map(Datensatz::getOrganisationseinheit).toList();
        List<Artikelstammdaten> artikelstammdatenList = artikelstammdatenRepository.findArtikelstammdatenByOeKurzIn(oekurzList);
        return createExcelFile(datensatzList, artikelstammdatenList, year, generateIlvDto.monat());
    }

    private byte[] createExcelFile(List<Datensatz> datensaetze, List<Artikelstammdaten> artikelstammdatenList, int year, int month) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row header = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        Cell headerCell = header.createCell(0);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("Jahr");
        headerCell = header.createCell(1);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("Modul");
        headerCell = header.createCell(2);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("PSP-Element");
        headerCell = header.createCell(3);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("Artikel");
        headerCell = header.createCell(4);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("Menge");
        headerCell = header.createCell(5);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("Istmenge");
        headerCell = header.createCell(6);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("LS-Position");
        headerCell = header.createCell(7);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("Monat");
        List<Row> rows = new ArrayList<>();
        for (int i = 0; i < datensaetze.size(); i++) {
            Datensatz datensatz = datensaetze.get(i);
            Optional<Artikelstammdaten> artikelstammdatenOptional = artikelstammdatenList.stream().filter(it -> it.getOeKurz() != null && it.getOeKurz().equals(datensatz.getOrganisationseinheit())).findFirst();
            if (artikelstammdatenOptional.isEmpty()) continue;
            Artikelstammdaten artikelstammdaten = artikelstammdatenOptional.get();
            Optional<Row> existingRow = rows.stream().filter(r -> r.getCell(1).getStringCellValue().equals(artikelstammdaten.getModulbezeichnung()) && r.getCell(2).getStringCellValue().equals(datensatz.getZusatzInfos().getPspElement()) && r.getCell(3).getStringCellValue().equals(artikelstammdaten.getArtikelnummer())).findFirst();
            if (existingRow.isPresent()) {
                int existingRowIndex = rows.indexOf(existingRow.get());
                sheet.getRow(existingRowIndex + 1).getCell(4).setCellValue(sheet.getRow(existingRowIndex + 1).getCell(4).getNumericCellValue() + datensatz.getGesamtpreis());
                continue;
            }
            Row row = sheet.createRow(i + 1);
            setCellValue(row, 0, String.valueOf(year));
            setCellValue(row, 1, artikelstammdaten.getModulbezeichnung());
            setCellValue(row, 2, datensatz.getZusatzInfos().getPspElement());
            setCellValue(row, 3, artikelstammdaten.getArtikelnummer());
            setCellValue(row, 4, datensatz.getGesamtpreis());
            setCellValue(row, 5, 1);
            setCellValue(row, 6, "");
            setCellValue(row, 7, String.valueOf(month));
            rows.add(row);
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            workbook.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return os.toByteArray();
    }

    private void setCellValue(Row row, int index, String value) {
        if (value == null) return;
        row.createCell(index).setCellValue(value);
    }

    private void setCellValue(Row row, int index, Integer value) {
        if (value == null) return;
        row.createCell(index).setCellValue(value);
    }

    private void setCellValue(Row row, int index, Double value) {
        if (value == null) return;
        row.createCell(index).setCellValue(value);
    }
}
