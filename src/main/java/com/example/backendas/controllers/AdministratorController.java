package com.example.backendas.controllers;

import com.example.backendas.common.ApiResponse;
import com.example.backendas.dtos.administrator.AdministratorRequest;
import com.example.backendas.dtos.administrator.AdministratorResponse;
import com.example.backendas.services.AdministratorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administrators")
@RequiredArgsConstructor
public class AdministratorController {

    private final AdministratorService service;

    @PostMapping
    public ResponseEntity<ApiResponse<AdministratorResponse>> create(@Valid @RequestBody AdministratorRequest request) {
        AdministratorResponse created = service.create(request);
        return ResponseEntity.ok(ApiResponse.ok("Administrador creado correctamente", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AdministratorResponse>> update(
            @PathVariable Long id, @Valid @RequestBody AdministratorRequest request) {
        AdministratorResponse updated = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Administrador actualizado correctamente", updated));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AdministratorResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("Lista de administradores", service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AdministratorResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Administrador encontrado", service.findById(id)));
    }

    @GetMapping("/condominium/{condominiumId}")
    public ResponseEntity<ApiResponse<List<AdministratorResponse>>> findByCondominiumId(@PathVariable Long condominiumId) {
        return ResponseEntity.ok(ApiResponse.ok("Administradores del condominio", service.findByCondominiumId(condominiumId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Administrador eliminado correctamente", null));
    }
}

