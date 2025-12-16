package com.example.backendas.controllers;

import com.example.backendas.common.ApiResponse;
import com.example.backendas.dtos.incident.IncidentRequest;
import com.example.backendas.dtos.incident.IncidentResponse;
import com.example.backendas.entities.Incident;
import com.example.backendas.services.IncidentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/incidents")
@RequiredArgsConstructor
public class IncidentController {

    private final IncidentService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ADMINISTRATOR', 'RESIDENT')")
    public ResponseEntity<ApiResponse<IncidentResponse>> create(@Valid @RequestBody IncidentRequest request) {
        IncidentResponse created = service.create(request);
        return ResponseEntity.ok(ApiResponse.ok("Incidencia registrada correctamente", created));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<IncidentResponse>> update(
            @PathVariable Long id, @Valid @RequestBody IncidentRequest request) {
        IncidentResponse updated = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Incidencia actualizada correctamente", updated));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<IncidentResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("Lista de incidencias", service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<IncidentResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Incidencia encontrada", service.findById(id)));
    }

    @GetMapping("/resident/{residentId}")
    public ResponseEntity<ApiResponse<List<IncidentResponse>>> findByResidentId(@PathVariable Long residentId) {
        return ResponseEntity.ok(ApiResponse.ok("Incidencias del residente", service.findByResidentId(residentId)));
    }

    @GetMapping("/unit/{unitId}")
    public ResponseEntity<ApiResponse<List<IncidentResponse>>> findByUnitId(@PathVariable Long unitId) {
        return ResponseEntity.ok(ApiResponse.ok("Incidencias de la unidad", service.findByUnitId(unitId)));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<IncidentResponse>>> findByStatus(@PathVariable Incident.IncidentStatus status) {
        return ResponseEntity.ok(ApiResponse.ok("Incidencias por estado", service.findByStatus(status)));
    }

    @PostMapping("/{id}/resolve")
    @PreAuthorize("hasAnyRole('ADMIN', 'ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<IncidentResponse>> resolve(
            @PathVariable Long id, @RequestBody Map<String, String> request) {
        String resolutionNotes = request.get("resolutionNotes");
        IncidentResponse resolved = service.resolve(id, resolutionNotes);
        return ResponseEntity.ok(ApiResponse.ok("Incidencia resuelta correctamente", resolved));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Incidencia eliminada correctamente", null));
    }
}

