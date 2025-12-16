package com.example.backendas.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "incidents")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDateTime reportedDate;

    private LocalDateTime resolvedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncidentStatus status; // OPEN, IN_PROGRESS, RESOLVED, CANCELLED

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncidentType type; // MAINTENANCE, SECURITY, NOISE, OTHER

    private String resolutionNotes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resident_id", nullable = false)
    private Resident resident;

    public enum IncidentStatus {
        OPEN, IN_PROGRESS, RESOLVED, CANCELLED
    }

    public enum IncidentType {
        MAINTENANCE, SECURITY, NOISE, OTHER
    }
}

