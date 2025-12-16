package com.example.backendas.dtos.resident;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResidentRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 20, message = "El nombre no puede exceder los 20 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+$", message = "El nombre solo debe contener letras")
    private String name;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 20, message = "El apellido no puede exceder los 20 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+$", message = "El apellido solo debe contener letras")
    private String lastName;

    @NotBlank(message = "El correo es obligatorio")
    @Size(max = 30, message = "El correo no puede exceder los 30 caracteres")
    @Email(message = "Debe proporcionar un correo válido")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "El correo contiene caracteres no permitidos")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(min = 8, max = 8,  message = "El teléfono debe tener 8 caracteres")
    @Pattern(regexp = "^[0-9]+$", message = "El teléfono solo debe contener números")
    private String phone;

    @NotBlank(message = "El número de identificación es obligatorio")
    @Size(min = 8, max = 8, message = "El número de identificación debe tener 8 caracteres")
    @Pattern(regexp = "^[0-9]+$", message = "El número de identificación solo debe contener números")
    private String identificationNumber;

    private Long unitId; // Opcional, puede asignarse después
}
