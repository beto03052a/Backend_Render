package com.example.backendas.controllers;

import com.example.backendas.common.ApiResponse;
import com.example.backendas.dtos.resident.ResidentRequest;
import com.example.backendas.dtos.resident.ResidentResponse;
import com.example.backendas.services.ResidentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/residents")
@RequiredArgsConstructor
public class ResidentController {

    private final ResidentService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ADMINISTRATOR', 'RESIDENT')")
    public ResponseEntity<ApiResponse<ResidentResponse>> create(@Valid @RequestBody ResidentRequest request) {
        ResidentResponse created = service.create(request);
        return ResponseEntity.ok(ApiResponse.ok("Residente creado correctamente", created));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<ResidentResponse>> update(
            @PathVariable Long id, @Valid @RequestBody ResidentRequest request) {
        ResidentResponse updated = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Residente actualizado correctamente", updated));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ResidentResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("Lista de residentes", service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResidentResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Residente encontrado", service.findById(id)));
    }

    @PostMapping("/{residentId}/assign-unit/{unitId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<ResidentResponse>> assignToUnit(
            @PathVariable Long residentId, @PathVariable Long unitId) {
        ResidentResponse updated = service.assignToUnit(residentId, unitId);
        return ResponseEntity.ok(ApiResponse.ok("Residente asignado a unidad correctamente", updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Residente eliminado correctamente", null));
    }
}

