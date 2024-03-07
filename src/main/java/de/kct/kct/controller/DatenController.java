package de.kct.kct.controller;

import de.kct.kct.entity.Datensatz;
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
@RequestMapping("data")
public class DatenController {
    private DatenService datenService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadData(@RequestParam("file") MultipartFile file) {
        this.datenService.saveData(file);
        return ResponseEntity
                .ok(Map.of("message", "Data uploaded and saved to database successfully"));
    }

    @GetMapping
    public ResponseEntity<List<Datensatz>> getData() {
        return new ResponseEntity<>(datenService.getData(), HttpStatus.OK);
    }

}
