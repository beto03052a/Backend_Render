package com.example.backendas.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    /**
     * Correo electrónico del usuario
     */
    private String email;

    /**
     * Contraseña del usuario
     */
    private String password;
}

