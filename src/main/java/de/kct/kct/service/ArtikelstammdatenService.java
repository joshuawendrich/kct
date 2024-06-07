package de.kct.kct.service;

import de.kct.kct.entity.Artikelstammdaten;
import de.kct.kct.repository.ArtikelstammdatenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ArtikelstammdatenService {
    private ArtikelstammdatenRepository artikelstammdatenRepository;
    private ExcelUploadService excelUploadService;

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
}
