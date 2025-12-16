package com.example.backendas.repositories;

import com.example.backendas.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByResidentId(Long residentId);
    List<Payment> findByUnitId(Long unitId);
    List<Payment> findByStatus(Payment.PaymentStatus status);
    List<Payment> findByDueDateBeforeAndStatus(LocalDate date, Payment.PaymentStatus status);
}

