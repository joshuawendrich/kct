package de.kct.kct.repository;

import de.kct.kct.entity.User;
import de.kct.kct.entity.UserKostenstelle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserKostenstelleRepository extends JpaRepository<UserKostenstelle, Integer> {
    List<UserKostenstelle> findByUser(User user);
}
