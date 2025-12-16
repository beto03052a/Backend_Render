package com.example.backendas.repositories;

import com.example.backendas.entities.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    boolean existsByEmail(String email);
    List<Administrator> findByCondominiumId(Long condominiumId);
}

