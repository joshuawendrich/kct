package de.kct.kct.service;

import de.kct.kct.entity.Datensatz;
import de.kct.kct.repository.DatensatzRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class DatenService {
    private DatensatzRepository datensatzRepository;

    public void saveData(MultipartFile file){
        if(ExcelUploadService.isValidExcelFile(file)){
            try {
                List<Datensatz> customers = ExcelUploadService.getDataFromExcel(file.getInputStream());
                this.datensatzRepository.saveAll(customers);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }

    public List<Datensatz> getData(){
        return datensatzRepository.findAll();
    }
}
