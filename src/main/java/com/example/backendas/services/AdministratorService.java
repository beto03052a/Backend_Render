package com.example.backendas.services;

import com.example.backendas.dtos.administrator.AdministratorRequest;
import com.example.backendas.dtos.administrator.AdministratorResponse;

import java.util.List;

public interface AdministratorService {
    AdministratorResponse create(AdministratorRequest request);
    AdministratorResponse update(Long id, AdministratorRequest request);
    void delete(Long id);
    AdministratorResponse findById(Long id);
    List<AdministratorResponse> findAll();
    List<AdministratorResponse> findByCondominiumId(Long condominiumId);
}

