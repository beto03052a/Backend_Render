package com.example.backendas.servicesImpl;

import com.example.backendas.dtos.reservation.ReservationRequest;
import com.example.backendas.dtos.reservation.ReservationResponse;
import com.example.backendas.entities.Reservation;
import com.example.backendas.entities.Resident;
import com.example.backendas.exceptions.BusinessRuleException;
import com.example.backendas.exceptions.ResourceNotFoundException;
import com.example.backendas.mappers.ReservationMapper;
import com.example.backendas.repositories.ReservationRepository;
import com.example.backendas.repositories.ResidentRepository;
import com.example.backendas.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;
    private final ResidentRepository residentRepository;
    private final ReservationMapper mapper;

    @Override
    public ReservationResponse create(ReservationRequest request) {
        if (request.getEndDateTime().isBefore(request.getStartDateTime())) {
            throw new BusinessRuleException("La fecha de fin debe ser posterior a la fecha de inicio");
        }

        Resident resident = residentRepository.findById(request.getResidentId())
                .orElseThrow(() -> new ResourceNotFoundException("Residente no encontrado con ID: " + request.getResidentId()));

        // Verificar conflictos de horario
        List<Reservation> conflicting = repository.findByAreaNameAndStartDateTimeBetween(
                request.getAreaName(),
                request.getStartDateTime(),
                request.getEndDateTime()
        );
        
        if (!conflicting.isEmpty()) {
            throw new BusinessRuleException("Ya existe una reserva para este área en el horario especificado");
        }

        Reservation entity = mapper.toEntity(request);
        entity.setResident(resident);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public ReservationResponse update(Long id, ReservationRequest request) {
        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con ID: " + id));

        if (request.getEndDateTime().isBefore(request.getStartDateTime())) {
            throw new BusinessRuleException("La fecha de fin debe ser posterior a la fecha de inicio");
        }

        mapper.updateEntityFromRequest(request, reservation);
        return mapper.toResponse(repository.save(reservation));
    }

    @Override
    public List<ReservationResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public ReservationResponse findById(Long id) {
        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con ID: " + id));
        return mapper.toResponse(reservation);
    }

    @Override
    public List<ReservationResponse> findByResidentId(Long residentId) {
        return repository.findByResidentId(residentId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public ReservationResponse confirm(Long id) {
        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con ID: " + id));
        reservation.setStatus(Reservation.ReservationStatus.CONFIRMED);
        return mapper.toResponse(repository.save(reservation));
    }

    @Override
    public ReservationResponse cancel(Long id) {
        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con ID: " + id));
        reservation.setStatus(Reservation.ReservationStatus.CANCELLED);
        return mapper.toResponse(repository.save(reservation));
    }

    @Override
    public void delete(Long id) {
        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con ID: " + id));
        repository.delete(reservation);
    }
}

