package de.kct.kct.repository;

import de.kct.kct.entity.Datensatz;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DatensatzRepository extends JpaRepository<Datensatz, Integer> {
    @Query("SELECT d FROM Datensatz d")
    List<Datensatz> findDatensaetze(Pageable pageable);

}
