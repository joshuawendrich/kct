package de.kct.kct.repository;

import de.kct.kct.entity.Datensatz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatensatzRepository extends JpaRepository<Datensatz, Integer> {
}
