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
import org.springframework.data.domain.Pageable;
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

    public List<DatensatzDto> getData(User user, String kostenstelle, Integer page, Integer pageSize) {
        if (kostenstelle != null && user.getKostenstellen().stream().noneMatch(k -> k.getKostenstelle().equals(kostenstelle)))
            throw new RuntimeException();
        var kostenstellen = kostenstelle == null ? user.getKostenstellen().stream().map(UserKostenstelle::getKostenstelle).toList() : List.of(kostenstelle);
        return datensatzRepository.findDatensaetzeForKostenstellen(kostenstellen, Pageable.unpaged()).stream().map(DatensatzDto::fromDatensatz).toList();
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
