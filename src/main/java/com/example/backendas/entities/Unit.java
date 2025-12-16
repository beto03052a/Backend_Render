package com.example.backendas.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "units")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String unitNumber; // Ej: "A-101", "B-205"

    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false)
    private Double area; // Área en metros cuadrados

    private String type; // Ej: "Apartamento", "Casa", "Local"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condominium_id", nullable = false)
    private Condominium condominium;

    @OneToOne(mappedBy = "unit", fetch = FetchType.LAZY)
    private Resident resident;

    @OneToMany(mappedBy = "unit", fetch = FetchType.LAZY)
    private List<Payment> payments;

    @OneToMany(mappedBy = "unit", fetch = FetchType.LAZY)
    private List<Incident> incidents;
}

