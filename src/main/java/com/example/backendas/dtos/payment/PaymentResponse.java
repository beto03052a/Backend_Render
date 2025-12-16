package com.example.backendas.dtos.payment;

import com.example.backendas.entities.Payment;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private Long id;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private LocalDate dueDate;
    private Payment.PaymentStatus status;
    private String description;
    private String paymentMethod;
    private Long unitId;
    private String unitNumber;
    private Long residentId;
    private String residentName;
}

