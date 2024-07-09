package de.kct.kct.repository;

import de.kct.kct.entity.Artikelstammdaten;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtikelstammdatenRepository extends JpaRepository<Artikelstammdaten, String> {
    List<Artikelstammdaten> findArtikelstammdatenByOeKurzIn(List<String> oeKurzList);
}
