package com.example.backendas.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "residents")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false, unique = true)
    private String identificationNumber; // DNI, Cédula, etc.

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", unique = true)
    private Unit unit;

    @OneToMany(mappedBy = "resident", fetch = FetchType.LAZY)
    private List<Payment> payments;

    @OneToMany(mappedBy = "resident", fetch = FetchType.LAZY)
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "resident", fetch = FetchType.LAZY)
    private List<Incident> incidents;
}

