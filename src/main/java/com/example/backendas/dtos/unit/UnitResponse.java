package com.example.backendas.dtos.unit;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnitResponse {

    private Long id;
    private String unitNumber;
    private Integer floor;
    private Double area;
    private String type;
    private Long condominiumId;
    private String condominiumName;
    private Long residentId;
    private String residentName;
    private Boolean hasResident;
}

