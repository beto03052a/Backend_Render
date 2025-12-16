package com.example.backendas.repositories;

import com.example.backendas.entities.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {
    boolean existsByEmail(String email);
    boolean existsByIdentificationNumber(String identificationNumber);
    Optional<Resident> findByEmail(String email);
    Optional<Resident> findByIdentificationNumber(String identificationNumber);
    Optional<Resident> findByUnitId(Long unitId);
}

