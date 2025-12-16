package com.example.backendas.dtos.condominium;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CondominiumResponse {

    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private Integer totalUnits;
    private Integer totalAdministrators;
}

