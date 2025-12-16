package com.example.backendas.services;

import com.example.backendas.dtos.payment.PaymentRequest;
import com.example.backendas.dtos.payment.PaymentResponse;
import com.example.backendas.entities.Payment;

import java.util.List;

public interface PaymentService {
    PaymentResponse create(PaymentRequest request);
    PaymentResponse update(Long id, PaymentRequest request);
    void delete(Long id);
    PaymentResponse findById(Long id);
    List<PaymentResponse> findAll();
    List<PaymentResponse> findByResidentId(Long residentId);
    List<PaymentResponse> findByUnitId(Long unitId);
    List<PaymentResponse> findByStatus(Payment.PaymentStatus status);
    List<PaymentResponse> findOverduePayments();
}

