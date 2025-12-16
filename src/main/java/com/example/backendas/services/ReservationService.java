package com.example.backendas.services;

import com.example.backendas.dtos.reservation.ReservationRequest;
import com.example.backendas.dtos.reservation.ReservationResponse;
import com.example.backendas.entities.Reservation;

import java.util.List;

public interface ReservationService {
    ReservationResponse create(ReservationRequest request);
    ReservationResponse update(Long id, ReservationRequest request);
    void delete(Long id);
    ReservationResponse findById(Long id);
    List<ReservationResponse> findAll();
    List<ReservationResponse> findByResidentId(Long residentId);
    ReservationResponse confirm(Long id);
    ReservationResponse cancel(Long id);
}

