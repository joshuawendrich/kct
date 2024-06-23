package de.kct.kct.controller;

import de.kct.kct.service.ArtikelstammdatenService;
import lombok.AllArgsConstructor;
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
}