package com.afetlink.backend.repository;

import com.afetlink.backend.model.AidRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AidRequestRepository extends JpaRepository<AidRequest, Long> {
    
}