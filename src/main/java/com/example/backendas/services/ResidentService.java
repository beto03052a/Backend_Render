package com.example.backendas.services;

import com.example.backendas.dtos.resident.ResidentRequest;
import com.example.backendas.dtos.resident.ResidentResponse;

import java.util.List;

public interface ResidentService {
    ResidentResponse create(ResidentRequest request);
    ResidentResponse update(Long id, ResidentRequest request);
    void delete(Long id);
    ResidentResponse findById(Long id);
    List<ResidentResponse> findAll();
    ResidentResponse assignToUnit(Long residentId, Long unitId);
}

