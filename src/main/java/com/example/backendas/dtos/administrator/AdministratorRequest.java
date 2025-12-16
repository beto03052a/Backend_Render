package com.example.backendas.dtos.administrator;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdministratorRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 15, message = "El nombre no puede exceder los 15 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+$", message = "El nombre solo debe contener letras (sin espacios)")
    private String name;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 15, message = "El apellido no puede exceder los 15 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+$", message = "El apellido solo debe contener letras (sin espacios)")
    private String lastName;

    @NotBlank(message = "El correo es obligatorio")
    @Size(max = 50, message = "El correo no puede exceder los 50 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "El correo contiene caracteres no permitidos (solo letras, números, puntos, guiones y @)")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(min = 8, max = 8, message = "El teléfono debe tener exactamente 8 dígitos (sin espacios)")
    @Pattern(regexp = "^[0-9]+$", message = "El teléfono solo debe contener números (sin espacios ni símbolos)")
    private String phone;

    @NotBlank(message = "El cargo es obligatorio")
    private String position;

    @NotNull(message = "El ID del condominio es obligatorio")
    private Long condominiumId;
}

