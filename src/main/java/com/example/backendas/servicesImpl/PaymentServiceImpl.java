package com.example.backendas.servicesImpl;

import com.example.backendas.dtos.payment.PaymentRequest;
import com.example.backendas.dtos.payment.PaymentResponse;
import com.example.backendas.entities.Payment;
import com.example.backendas.entities.Resident;
import com.example.backendas.entities.Unit;
import com.example.backendas.exceptions.BusinessRuleException;
import com.example.backendas.exceptions.ResourceNotFoundException;
import com.example.backendas.mappers.PaymentMapper;
import com.example.backendas.repositories.PaymentRepository;
import com.example.backendas.repositories.ResidentRepository;
import com.example.backendas.repositories.UnitRepository;
import com.example.backendas.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;
    private final UnitRepository unitRepository;
    private final ResidentRepository residentRepository;
    private final PaymentMapper mapper;

    @Override
    public PaymentResponse create(PaymentRequest request) {
        Unit unit = unitRepository.findById(request.getUnitId())
                .orElseThrow(() -> new ResourceNotFoundException("Unidad no encontrada con ID: " + request.getUnitId()));

        Resident resident = residentRepository.findById(request.getResidentId())
                .orElseThrow(() -> new ResourceNotFoundException("Residente no encontrado con ID: " + request.getResidentId()));

        if (!resident.getUnit().getId().equals(unit.getId())) {
            throw new BusinessRuleException("El residente no pertenece a la unidad especificada");
        }

        Payment entity = mapper.toEntity(request);
        entity.setUnit(unit);
        entity.setResident(resident);

        // Actualizar status si la fecha de vencimiento ya pasó
        if (entity.getDueDate().isBefore(LocalDate.now()) && 
            entity.getStatus() == Payment.PaymentStatus.PENDING) {
            entity.setStatus(Payment.PaymentStatus.OVERDUE);
        }

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public PaymentResponse update(Long id, PaymentRequest request) {
        Payment payment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con ID: " + id));

        if (request.getUnitId() != null && !request.getUnitId().equals(payment.getUnit().getId())) {
            Unit unit = unitRepository.findById(request.getUnitId())
                    .orElseThrow(() -> new ResourceNotFoundException("Unidad no encontrada con ID: " + request.getUnitId()));
            payment.setUnit(unit);
        }

        if (request.getResidentId() != null && !request.getResidentId().equals(payment.getResident().getId())) {
            Resident resident = residentRepository.findById(request.getResidentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Residente no encontrado con ID: " + request.getResidentId()));
            payment.setResident(resident);
        }

        mapper.updateEntityFromRequest(request, payment);
        return mapper.toResponse(repository.save(payment));
    }

    @Override
    public List<PaymentResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public PaymentResponse findById(Long id) {
        Payment payment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con ID: " + id));
        return mapper.toResponse(payment);
    }

    @Override
    public List<PaymentResponse> findByResidentId(Long residentId) {
        return repository.findByResidentId(residentId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<PaymentResponse> findByUnitId(Long unitId) {
        return repository.findByUnitId(unitId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<PaymentResponse> findByStatus(Payment.PaymentStatus status) {
        return repository.findByStatus(status).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<PaymentResponse> findOverduePayments() {
        return repository.findByDueDateBeforeAndStatus(LocalDate.now(), Payment.PaymentStatus.PENDING).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Payment payment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con ID: " + id));
        repository.delete(payment);
    }
}

