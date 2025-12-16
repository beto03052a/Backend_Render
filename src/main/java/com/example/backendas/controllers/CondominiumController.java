package com.example.backendas.controllers;

import com.example.backendas.common.ApiResponse;
import com.example.backendas.dtos.condominium.CondominiumRequest;
import com.example.backendas.dtos.condominium.CondominiumResponse;
import com.example.backendas.services.CondominiumService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/condominiums")
@RequiredArgsConstructor
public class CondominiumController {

    private final CondominiumService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<CondominiumResponse>> create(@Valid @RequestBody CondominiumRequest request) {
        CondominiumResponse created = service.create(request);
        return ResponseEntity.ok(ApiResponse.ok("Condominio creado correctamente", created));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<CondominiumResponse>> update(
            @PathVariable Long id, @Valid @RequestBody CondominiumRequest request) {
        CondominiumResponse updated = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Condominio actualizado correctamente", updated));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CondominiumResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("Lista de condominios", service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CondominiumResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Condominio encontrado", service.findById(id)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Condominio eliminado correctamente", null));
    }
}

