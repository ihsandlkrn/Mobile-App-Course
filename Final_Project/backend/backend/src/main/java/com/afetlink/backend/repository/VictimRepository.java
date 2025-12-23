package com.afetlink.backend.repository;

import com.afetlink.backend.model.Victim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VictimRepository extends JpaRepository<Victim, Long> {
    // Finds a victim by their email address
    Optional<Victim> findByEmail(String email);
}