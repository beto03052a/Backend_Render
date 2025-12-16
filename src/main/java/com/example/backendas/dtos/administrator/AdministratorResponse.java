package com.example.backendas.dtos.administrator;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdministratorResponse {

    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String position;
    private Long condominiumId;
    private String condominiumName;
}

