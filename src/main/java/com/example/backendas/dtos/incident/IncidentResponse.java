package com.example.backendas.dtos.incident;

import com.example.backendas.entities.Incident;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncidentResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime reportedDate;
    private LocalDateTime resolvedDate;
    private Incident.IncidentStatus status;
    private Incident.IncidentType type;
    private String resolutionNotes;
    private Long unitId;
    private String unitNumber;
    private Long residentId;
    private String residentName;
}

