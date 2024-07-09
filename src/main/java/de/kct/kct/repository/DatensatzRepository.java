package de.kct.kct.repository;

import de.kct.kct.entity.Datensatz;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DatensatzRepository extends JpaRepository<Datensatz, Integer> {
    @Query("SELECT d FROM Datensatz d WHERE d.kostenstelle IN ?1")
    List<Datensatz> findDatensaetzeForKostenstellen(List<String> kostenstellen, Pageable pageable);

    @Query("SELECT d FROM Datensatz d WHERE d.detailangabe1 = ?1 AND d.nutzer = ?2 AND d.zusatzInfos.pspElement IS NOT NULL AND d.zusatzInfos.pspElement != ''")
    List<Datensatz> findDatensaetzeForDetailAndNutzer(String detailangabe1, String nutzer);

    @Query("SELECT d FROM Datensatz d WHERE d.kostenstelle IN ?1 AND d.zusatzInfos.abgerechnetMonat = ?2 AND d.zusatzInfos.abgerechnetJahr = ?3")
    List<Datensatz> findDatensaetzeForKostenstellenAndMonth(List<String> kostenstellen, Integer abgerechnetMonat, Integer abgerechnetJahr);
}
