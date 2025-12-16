package com.example.backendas.dtos.payment;

import com.example.backendas.validators.ValidPaymentDates;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ValidPaymentDates
public class PaymentRequest {

    @NotNull(message = "El monto solo debe tener numeros")
    @Positive(message = "El monto debe ser positivo")
    @Digits(integer = 10, fraction = 0, message = "El monto no puede exceder los 10 dígitos")
    private BigDecimal amount;

    @NotNull(message = "La fecha de pago es obligatoria")
    private LocalDate paymentDate;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    private LocalDate dueDate;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 50, message = "La descripción no puede exceder los 50 caracteres")
    private String description;

    @NotBlank(message = "El método de pago es obligatorio")
    @Size(max = 20, message = "El método de pago no puede exceder los 20 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+$", message = "El método de pago solo debe contener letras")
    private String paymentMethod;

    @NotNull(message = "El ID de la unidad es obligatorio")
    private Long unitId;

    @NotNull(message = "El ID del residente es obligatorio")
    private Long residentId;
}

