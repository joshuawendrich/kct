package de.kct.kct.repository;

import de.kct.kct.entity.Datensatz;
import de.kct.kct.entity.ZusatzInfos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ZusatzInfosRepository extends JpaRepository<ZusatzInfos, Integer> {
    Optional<ZusatzInfos> findByDatensatz(Datensatz datensatz);
}
