package com.example.backendas.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "condominiums")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Condominium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    private String phone;
    private String email;

    @OneToMany(mappedBy = "condominium", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Unit> units;

    @OneToMany(mappedBy = "condominium", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Administrator> administrators;
}

