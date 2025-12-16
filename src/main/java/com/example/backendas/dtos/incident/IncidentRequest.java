package com.example.backendas.dtos.incident;

import com.example.backendas.entities.Incident;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncidentRequest {

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 20, message = "El título no puede exceder los 20 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ.;]+$", message = "El título solo debe contener letras, puntos y comas (sin espacios)")
    private String title;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 100, message = "La descripción no puede exceder los 100 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ .;]+$", message = "La descripción solo debe contener letras, espacios, puntos y comas")
    private String description;

    @NotNull(message = "El tipo de incidencia es obligatorio")
    private Incident.IncidentType type;

    private Incident.IncidentStatus status;

    private String priority;

    @NotNull(message = "El ID de la unidad es obligatorio")
    private Long unitId;

    @NotNull(message = "El ID del residente es obligatorio")
    private Long residentId;
}

