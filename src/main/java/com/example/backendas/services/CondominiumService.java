package com.example.backendas.services;

import com.example.backendas.dtos.condominium.CondominiumRequest;
import com.example.backendas.dtos.condominium.CondominiumResponse;

import java.util.List;

public interface CondominiumService {
    CondominiumResponse create(CondominiumRequest request);
    CondominiumResponse update(Long id, CondominiumRequest request);
    void delete(Long id);
    CondominiumResponse findById(Long id);
    List<CondominiumResponse> findAll();
}

