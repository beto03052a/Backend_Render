package com.example.backendas.controllers;

import com.example.backendas.common.ApiResponse;
import com.example.backendas.dtos.unit.UnitRequest;
import com.example.backendas.dtos.unit.UnitResponse;
import com.example.backendas.services.UnitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/units")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<UnitResponse>> create(@Valid @RequestBody UnitRequest request) {
        UnitResponse created = service.create(request);
        return ResponseEntity.ok(ApiResponse.ok("Unidad creada correctamente", created));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<UnitResponse>> update(
            @PathVariable Long id, @Valid @RequestBody UnitRequest request) {
        UnitResponse updated = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Unidad actualizada correctamente", updated));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UnitResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("Lista de unidades", service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UnitResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Unidad encontrada", service.findById(id)));
    }

    @GetMapping("/condominium/{condominiumId}")
    public ResponseEntity<ApiResponse<List<UnitResponse>>> findByCondominiumId(@PathVariable Long condominiumId) {
        return ResponseEntity.ok(ApiResponse.ok("Unidades del condominio", service.findByCondominiumId(condominiumId)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Unidad eliminada correctamente", null));
    }
}

