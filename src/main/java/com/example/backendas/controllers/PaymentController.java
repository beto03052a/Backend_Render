package com.example.backendas.controllers;

import com.example.backendas.common.ApiResponse;
import com.example.backendas.dtos.payment.PaymentRequest;
import com.example.backendas.dtos.payment.PaymentResponse;
import com.example.backendas.entities.Payment;
import com.example.backendas.services.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> create(@Valid @RequestBody PaymentRequest request) {
        PaymentResponse created = service.create(request);
        return ResponseEntity.ok(ApiResponse.ok("Pago registrado correctamente", created));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<PaymentResponse>> update(
            @PathVariable Long id, @Valid @RequestBody PaymentRequest request) {
        PaymentResponse updated = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Pago actualizado correctamente", updated));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("Lista de pagos", service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Pago encontrado", service.findById(id)));
    }

    @GetMapping("/resident/{residentId}")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> findByResidentId(@PathVariable Long residentId) {
        return ResponseEntity.ok(ApiResponse.ok("Pagos del residente", service.findByResidentId(residentId)));
    }

    @GetMapping("/unit/{unitId}")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> findByUnitId(@PathVariable Long unitId) {
        return ResponseEntity.ok(ApiResponse.ok("Pagos de la unidad", service.findByUnitId(unitId)));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> findByStatus(@PathVariable Payment.PaymentStatus status) {
        return ResponseEntity.ok(ApiResponse.ok("Pagos por estado", service.findByStatus(status)));
    }

    @GetMapping("/overdue")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> findOverduePayments() {
        return ResponseEntity.ok(ApiResponse.ok("Pagos vencidos", service.findOverduePayments()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Pago eliminado correctamente", null));
    }
}

