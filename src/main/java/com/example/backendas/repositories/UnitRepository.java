package com.example.backendas.repositories;

import com.example.backendas.entities.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    boolean existsByUnitNumber(String unitNumber);
    Optional<Unit> findByUnitNumber(String unitNumber);
    List<Unit> findByCondominiumId(Long condominiumId);
    boolean existsByResidentId(Long residentId);
}

