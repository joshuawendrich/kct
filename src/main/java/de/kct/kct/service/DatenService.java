package de.kct.kct.service;

import de.kct.kct.entity.Datensatz;
import de.kct.kct.entity.ZusatzInfos;
import de.kct.kct.repository.DatensatzRepository;
import de.kct.kct.repository.ZusatzInfosRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public List<Datensatz> getData() {
        return datensatzRepository.findDatensaetze(PageRequest.of(0, 100));
    }

    private ZusatzInfos findZusatzInfos(Integer id) {
        Datensatz datensatz = datensatzRepository.findById(id).orElseThrow();
        Optional<ZusatzInfos> zusatzInfos = zusatzInfosRepository.findByDatensatz(datensatz);
        return zusatzInfos.orElseGet(ZusatzInfos::new);
    }

    public ZusatzInfos getZusatzInfosForDatensatz(Integer id) {
        return findZusatzInfos(id);
    }
}
