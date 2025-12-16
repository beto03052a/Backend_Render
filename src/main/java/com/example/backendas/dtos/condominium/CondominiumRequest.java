package com.example.backendas.dtos.condominium;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CondominiumRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 20, message = "El nombre no puede exceder los 20 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre solo debe contener letras, números y espacios")
    private String name;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 20, message = "La dirección no puede exceder los 20 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s,.-]+$", message = "La dirección solo debe contener letras, números, espacios y caracteres básicos (coma, punto, guion)")
    private String address;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(min = 8, max = 8, message = "El teléfono debe tener 8 digitos")
    @Pattern(regexp = "^[0-9]+$", message = "El teléfono solo debe contener números")
    private String phone;

    @NotBlank(message = "El email es obligatorio")
    @Size(max = 30, message = "El email no puede exceder los 30 caracteres")
    @Email(message = "Debe proporcionar un correo válido")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "El correo contiene caracteres no permitidos")
    private String email;
}
