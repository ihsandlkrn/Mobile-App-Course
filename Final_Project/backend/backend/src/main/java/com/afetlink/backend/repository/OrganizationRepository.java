package com.afetlink.backend.repository;

import com.afetlink.backend.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    // Finds an organization by their email address
    Optional<Organization> findByEmail(String email);
}