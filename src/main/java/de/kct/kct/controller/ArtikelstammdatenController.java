package de.kct.kct.controller;

import de.kct.kct.dto.GenerateIlvDto;
import de.kct.kct.service.ArtikelstammdatenService;
import de.kct.kct.util.UserUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/artikelstammdaten")
@AllArgsConstructor
public class ArtikelstammdatenController {

    private ArtikelstammdatenService artikelstammdatenService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Void> uploadArtikelstammdaten(@RequestParam MultipartFile file) {
        artikelstammdatenService.saveData(file);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<Void> getAllArtikelstammdaten() {
        artikelstammdatenService.getAll();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/ilv")
    ResponseEntity<byte[]> generateIlv(@RequestBody GenerateIlvDto generateIlvDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        headers.set("Content-Disposition", "attachment; filename=\"ilv.xlsx\"");
        return ResponseEntity.ok().headers(headers).body((artikelstammdatenService.generateIlv(UserUtils.getCurrentUser(), generateIlvDto)));

    }
}
