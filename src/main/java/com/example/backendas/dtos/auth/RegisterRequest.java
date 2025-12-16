package com.example.backendas.dtos.auth;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 15, message = "El nombre no puede exceder los 15 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+$", message = "El nombre solo debe contener letras (sin espacios)")
    private String firstname;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 15, message = "El apellido no puede exceder los 15 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+$", message = "El apellido solo debe contener letras (sin espacios)")
    private String lastname;

    @NotBlank(message = "El correo es obligatorio")
    @Size(max = 50, message = "El correo no puede exceder los 50 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "El correo contiene caracteres no permitidos (solo letras, números, puntos, guiones y @)")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Pattern(regexp = "^[^\\s]+$", message = "La contraseña no puede contener espacios")
    private String password;
    private String role; // ROLE_ADMIN, ROLE_RESIDENT, ROLE_ADMINISTRATOR
}

