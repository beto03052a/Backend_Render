package com.example.backendas.dtos.unit;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnitRequest {

    @NotBlank(message = "El número de unidad es obligatorio")
    @Size(max = 2, message = "El número de unidad no puede exceder los 2 caracteres")
    @Pattern(regexp = "^[0-9]+$", message = "El número de unidad solo debe contener números")
    private String unitNumber;

    @NotBlank(message = "El piso es obligatorio")
    @Size(max = 2, message = "El numero de piso no puede exceder los 2 caracteres")
    @Pattern(regexp = "^[0-9]+$", message = "El piso solo debe contener números")
    private String floor;

    @NotBlank(message = "El área es obligatoria")
    @Pattern(regexp = "^[0-9]+(\\.[0-9]+)?$", message = "El área solo debe contener números")
    private String area;

    @NotBlank(message = "El tipo es obligatorio")
    @Size(max = 20, message = "El tipo no puede exceder los 20 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El tipo solo debe contener letras")
    private String type; // Ej: "Apartamento", "Casa", "Local"

    @NotNull(message = "El ID del condominio es obligatorio")
    private Long condominiumId;
}
