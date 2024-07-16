package de.kct.kct.service;

import de.kct.kct.dto.DatensatzDto;
import de.kct.kct.dto.UpdateZusatzInfosDto;
import de.kct.kct.dto.ZusatzInfosDto;
import de.kct.kct.entity.Datensatz;
import de.kct.kct.entity.User;
import de.kct.kct.entity.UserKostenstelle;
import de.kct.kct.entity.ZusatzInfos;
import de.kct.kct.repository.DatensatzRepository;
import de.kct.kct.repository.ZusatzInfosRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DatenService {
    private DatensatzRepository datensatzRepository;
    private ZusatzInfosRepository zusatzInfosRepository;
    private ExcelUploadService excelUploadService;

    public void saveData(MultipartFile file) {
        if (excelUploadService.isValidExcelFile(file)) {
            try {
                List<Datensatz> customers = excelUploadService.getDataFromExcel(file.getInputStream());
                this.datensatzRepository.saveAll(customers);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }

    private void checkKostenstelle(User user, String kostenstelle) {
        if (kostenstelle != null && user.getKostenstellen().stream().noneMatch(k -> k.getKostenstelle().equals(kostenstelle)))
            throw new RuntimeException();
    }

    public List<DatensatzDto> getData(User user, String kostenstelle, Integer page, Integer pageSize) {
        checkKostenstelle(user, kostenstelle);
        var kostenstellen = kostenstelle == null ? user.getKostenstellen().stream().map(UserKostenstelle::getKostenstelle).toList() : List.of(kostenstelle);
        return datensatzRepository.findDatensaetzeForKostenstellen(kostenstellen, Pageable.unpaged()).stream().map(DatensatzDto::fromDatensatz).toList();
    }

    public byte[] downloadData(User user, String kostenstelle) {
        checkKostenstelle(user, kostenstelle);
        var kostenstellen = kostenstelle == null ? user.getKostenstellen().stream().map(UserKostenstelle::getKostenstelle).toList() : List.of(kostenstelle);
        var datensaetze = datensatzRepository.findDatensaetzeForKostenstellen(kostenstellen, Pageable.unpaged());
        return createExcelFile(datensaetze);
    }

    private byte[] createExcelFile(List<Datensatz> datensaetze) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row header = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        Cell headerCell = header.createCell(0);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("ID");
        headerCell = header.createCell(1);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("Detailangabe 1");
        headerCell = header.createCell(2);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("Detailangabe 2");
        headerCell = header.createCell(3);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("Produkt Leistung");
        headerCell = header.createCell(4);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("Nutzer");
        headerCell = header.createCell(5);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("Monat");
        headerCell = header.createCell(6);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("Jahr");
        headerCell = header.createCell(7);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("Kostenstelle");
        headerCell = header.createCell(8);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("Gesamtpreis");
        headerCell = header.createCell(9);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("Bemerkung");
        headerCell = header.createCell(10);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("PSP Element");
        headerCell = header.createCell(11);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("Abgerechnet Monat");
        for (int i = 0; i < datensaetze.size(); i++) {
            Datensatz datensatz = datensaetze.get(i);
            Row row = sheet.createRow(i + 1);
            setCellValue(row, 0, datensatz.getId().toString());
            setCellValue(row, 1, datensatz.getDetailangabe1());
            setCellValue(row, 2, datensatz.getDetailangabe2());
            setCellValue(row, 3, datensatz.getProduktLeistung());
            setCellValue(row, 4, datensatz.getNutzer());
            setCellValue(row, 5, datensatz.getMonat());
            setCellValue(row, 6, datensatz.getJahr());
            setCellValue(row, 7, datensatz.getKostenstelle());
            setCellValue(row, 8, datensatz.getGesamtpreis());
            var zusatzInfos = datensatz.getZusatzInfos();
            if (zusatzInfos == null) continue;
            setCellValue(row, 9, zusatzInfos.getBemerkung());
            setCellValue(row, 10, zusatzInfos.getPspElement());
            setCellValue(row, 11, zusatzInfos.getAbgerechnetMonat());
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

    private ZusatzInfos findZusatzInfos(Integer id) {
        Datensatz datensatz = datensatzRepository.findById(id).orElseThrow();
        Optional<ZusatzInfos> zusatzInfos = zusatzInfosRepository.findByDatensatz(datensatz);
        return zusatzInfos.orElseGet(ZusatzInfos::new);
    }

    private ZusatzInfos findOrCreateZusatzInfos(Integer id) {
        ZusatzInfos zusatzInfos = findZusatzInfos(id);
        if (zusatzInfos.getDatensatz() == null) {
            zusatzInfos.setDatensatz(datensatzRepository.getReferenceById(id));
        }
        return zusatzInfos;
    }

    public ZusatzInfosDto getZusatzInfosForDatensatz(Integer id) {
        return ZusatzInfosDto.fromZusatzInfos(findZusatzInfos(id));
    }

    public void updateZusatzInfos(Integer datensatzId, UpdateZusatzInfosDto updateZusatzInfosDto) {
        changeZusatzInfos(datensatzId, updateZusatzInfosDto, true);
        datensatzRepository.findOtherDatensaetze(datensatzId).forEach(ds -> changeZusatzInfos(ds.getId(), updateZusatzInfosDto, false));
    }

    private void changeZusatzInfos(Integer datensatzId, UpdateZusatzInfosDto updateZusatzInfosDto, boolean includeBemerkung) {
        ZusatzInfos zusatzInfos = findOrCreateZusatzInfos(datensatzId);
        if (includeBemerkung) zusatzInfos.setBemerkung(updateZusatzInfosDto.bemerkung());
        zusatzInfos.setPspElement(updateZusatzInfosDto.psp());
        zusatzInfos.setAbgerechnetMonat(updateZusatzInfosDto.abgerechnet());
        if (zusatzInfos.getPspElement() != null && !zusatzInfos.getPspElement().isEmpty()) {
            if (zusatzInfos.getAbgerechnetMonat() == null) {
                zusatzInfos.setAbgerechnetMonat(LocalDate.now().getMonthValue());
            }
            zusatzInfos.setAbgerechnetJahr(LocalDate.now().getYear());
        }
        zusatzInfosRepository.save(zusatzInfos);
    }
}
