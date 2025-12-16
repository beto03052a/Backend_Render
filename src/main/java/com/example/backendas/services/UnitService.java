package com.example.backendas.services;

import com.example.backendas.dtos.unit.UnitRequest;
import com.example.backendas.dtos.unit.UnitResponse;

import java.util.List;

public interface UnitService {
    UnitResponse create(UnitRequest request);
    UnitResponse update(Long id, UnitRequest request);
    void delete(Long id);
    UnitResponse findById(Long id);
    List<UnitResponse> findAll();
    List<UnitResponse> findByCondominiumId(Long condominiumId);
}

