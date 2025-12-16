package com.example.backendas.dtos.reservation;

import com.example.backendas.entities.Reservation;
import com.example.backendas.validators.ValidReservationDates;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ValidReservationDates
public class ReservationRequest {

    @NotBlank(message = "El área es obligatoria")
    @Size(max = 20, message = "El área no puede exceder los 20 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ .,]+$", message = "El área solo debe contener letras, espacios, puntos y comas")
    private String areaName;

    @NotNull(message = "La fecha y hora de inicio es obligatoria")
    private LocalDateTime startDateTime;

    @NotNull(message = "La fecha y hora de fin es obligatoria")
    private LocalDateTime endDateTime;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 100, message = "La descripción no puede exceder los 100 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ .,!?¿¡()-]+$", message = "La descripción contiene caracteres no permitidos")
    private String description;
    
    @NotNull(message = "El costo es obligatorio")
    @Positive(message = "El costo debe ser positivo")
    @Digits(integer = 10, fraction = 2, message = "El costo no puede exceder los 10 dígitos")
    private BigDecimal cost;

    private Reservation.ReservationStatus status;

    @NotNull(message = "El ID del residente es obligatorio")
    private Long residentId;
}

