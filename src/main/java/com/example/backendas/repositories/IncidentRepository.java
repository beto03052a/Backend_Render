package com.example.backendas.repositories;

import com.example.backendas.entities.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
    List<Incident> findByResidentId(Long residentId);
    List<Incident> findByUnitId(Long unitId);
    List<Incident> findByStatus(Incident.IncidentStatus status);
    List<Incident> findByType(Incident.IncidentType type);
}

