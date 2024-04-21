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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DatenService {
    private DatensatzRepository datensatzRepository;
    private ZusatzInfosRepository zusatzInfosRepository;

    public void saveData(MultipartFile file) {
        if (ExcelUploadService.isValidExcelFile(file)) {
            try {
                List<Datensatz> customers = ExcelUploadService.getDataFromExcel(file.getInputStream());
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
        var datensaetze =  datensatzRepository.findDatensaetzeForKostenstellen(kostenstellen, Pageable.unpaged());
        return createExcelFile(datensaetze);
    }

    private byte[] createExcelFile(List<Datensatz> datensaetze) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        for(int i = 0; i < datensaetze.size(); i++) {
            Datensatz datensatz = datensaetze.get(i);
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(datensatz.getId().toString());
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            workbook.write(os);
        } catch (Exception e){
            e.printStackTrace();
        }
        return os.toByteArray();
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
        ZusatzInfos zusatzInfos = findOrCreateZusatzInfos(datensatzId);
        zusatzInfos.setBemerkung(updateZusatzInfosDto.bemerkung());
        zusatzInfos.setPspElement(updateZusatzInfosDto.psp());
        zusatzInfos.setAbgerechnetMonat(updateZusatzInfosDto.abgerechnet());
        zusatzInfosRepository.save(zusatzInfos);
    }
}
