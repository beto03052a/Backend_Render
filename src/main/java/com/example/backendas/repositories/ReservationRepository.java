package com.example.backendas.repositories;

import com.example.backendas.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByResidentId(Long residentId);
    List<Reservation> findByStatus(Reservation.ReservationStatus status);
    List<Reservation> findByAreaNameAndStartDateTimeBetween(
            String areaName, LocalDateTime start, LocalDateTime end);
}

