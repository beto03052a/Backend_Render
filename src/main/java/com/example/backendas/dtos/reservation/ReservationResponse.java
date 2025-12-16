package com.example.backendas.dtos.reservation;

import com.example.backendas.entities.Reservation;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponse {

    private Long id;
    private String areaName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Reservation.ReservationStatus status;
    private String description;
    private BigDecimal cost;
    private Long residentId;
    private String residentName;
    private String unitNumber;
}

