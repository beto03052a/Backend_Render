package com.example.backendas.controllers;

import com.example.backendas.common.ApiResponse;
import com.example.backendas.dtos.reservation.ReservationRequest;
import com.example.backendas.dtos.reservation.ReservationResponse;
import com.example.backendas.services.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;

    @PostMapping
    public ResponseEntity<ApiResponse<ReservationResponse>> create(@Valid @RequestBody ReservationRequest request) {
        ReservationResponse created = service.create(request);
        return ResponseEntity.ok(ApiResponse.ok("Reserva creada correctamente", created));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<ReservationResponse>> update(
            @PathVariable Long id, @Valid @RequestBody ReservationRequest request) {
        ReservationResponse updated = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Reserva actualizada correctamente", updated));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("Lista de reservas", service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReservationResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Reserva encontrada", service.findById(id)));
    }

    @GetMapping("/resident/{residentId}")
    public ResponseEntity<ApiResponse<List<ReservationResponse>>> findByResidentId(@PathVariable Long residentId) {
        return ResponseEntity.ok(ApiResponse.ok("Reservas del residente", service.findByResidentId(residentId)));
    }

    @PostMapping("/{id}/confirm")
    @PreAuthorize("hasAnyRole('ADMIN', 'ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<ReservationResponse>> confirm(@PathVariable Long id) {
        ReservationResponse confirmed = service.confirm(id);
        return ResponseEntity.ok(ApiResponse.ok("Reserva confirmada correctamente", confirmed));
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN', 'ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<ReservationResponse>> cancel(@PathVariable Long id) {
        ReservationResponse cancelled = service.cancel(id);
        return ResponseEntity.ok(ApiResponse.ok("Reserva cancelada correctamente", cancelled));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Reserva eliminada correctamente", null));
    }
}

