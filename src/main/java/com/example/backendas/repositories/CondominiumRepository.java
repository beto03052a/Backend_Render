package com.example.backendas.repositories;

import com.example.backendas.entities.Condominium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CondominiumRepository extends JpaRepository<Condominium, Long> {
    boolean existsByName(String name);
    boolean existsByEmail(String email);
}

