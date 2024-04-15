package de.kct.kct.controller;

import de.kct.kct.dto.DatensatzDto;
import de.kct.kct.dto.UpdateZusatzInfosDto;
import de.kct.kct.dto.ZusatzInfosDto;
import de.kct.kct.entity.ZusatzInfos;
import de.kct.kct.service.DatenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/data")
public class DatenController {
    private DatenService datenService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadData(@RequestParam("file") MultipartFile file) {
        this.datenService.saveData(file);
        return ResponseEntity
                .ok(Map.of("message", "Data uploaded and saved to database successfully"));
    }

    @GetMapping
    public ResponseEntity<List<DatensatzDto>> getData() {
        return new ResponseEntity<>(datenService.getData(), HttpStatus.OK);
    }

    @GetMapping("/{id}/zusatz-infos")
    public ResponseEntity<ZusatzInfosDto> getZusatzInfosForDatensatz(@PathVariable Integer id) {
        return ResponseEntity.ok(datenService.getZusatzInfosForDatensatz(id));
    }

    @PutMapping("{id}/zusatz-infos")
    public ResponseEntity<Void> updateZusatzinfos(@PathVariable Integer id, @RequestBody UpdateZusatzInfosDto updateZusatzInfosDto) {
        datenService.updateZusatzInfos(id, updateZusatzInfosDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
