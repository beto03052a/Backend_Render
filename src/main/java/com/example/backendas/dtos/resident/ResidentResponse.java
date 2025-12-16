package com.example.backendas.dtos.resident;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResidentResponse {

    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String identificationNumber;
    private Long unitId;
    private String unitNumber;
    private String condominiumName;
    private Integer totalPayments;
    private Integer totalReservations;
    private Integer totalIncidents;
}

