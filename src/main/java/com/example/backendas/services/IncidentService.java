package com.example.backendas.services;

import com.example.backendas.dtos.incident.IncidentRequest;
import com.example.backendas.dtos.incident.IncidentResponse;
import com.example.backendas.entities.Incident;

import java.util.List;

public interface IncidentService {
    IncidentResponse create(IncidentRequest request);
    IncidentResponse update(Long id, IncidentRequest request);
    void delete(Long id);
    IncidentResponse findById(Long id);
    List<IncidentResponse> findAll();
    List<IncidentResponse> findByResidentId(Long residentId);
    List<IncidentResponse> findByUnitId(Long unitId);
    List<IncidentResponse> findByStatus(Incident.IncidentStatus status);
    IncidentResponse resolve(Long id, String resolutionNotes);
}

