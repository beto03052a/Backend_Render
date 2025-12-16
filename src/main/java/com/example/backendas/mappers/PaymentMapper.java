package com.example.backendas.mappers;

import com.example.backendas.dtos.payment.PaymentRequest;
import com.example.backendas.dtos.payment.PaymentResponse;
import com.example.backendas.entities.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper {

    default Payment toEntity(PaymentRequest dto) {
        if (dto == null) return null;
        Payment entity = new Payment();
        entity.setAmount(dto.getAmount());
        entity.setPaymentDate(dto.getPaymentDate());
        entity.setDueDate(dto.getDueDate());
        entity.setDescription(dto.getDescription());
        entity.setPaymentMethod(dto.getPaymentMethod());
        // Determinar status basado en fechas
        if (dto.getPaymentDate() != null && dto.getDueDate() != null) {
            if (dto.getPaymentDate().isBefore(dto.getDueDate()) || dto.getPaymentDate().isEqual(dto.getDueDate())) {
                entity.setStatus(Payment.PaymentStatus.PAID);
            } else {
                entity.setStatus(Payment.PaymentStatus.OVERDUE);
            }
        } else {
            entity.setStatus(Payment.PaymentStatus.PENDING);
        }
        return entity;
    }

    default PaymentResponse toResponse(Payment entity) {
        if (entity == null) return null;
        PaymentResponse dto = new PaymentResponse();
        dto.setId(entity.getId());
        dto.setAmount(entity.getAmount());
        dto.setPaymentDate(entity.getPaymentDate());
        dto.setDueDate(entity.getDueDate());
        dto.setStatus(entity.getStatus());
        dto.setDescription(entity.getDescription());
        dto.setPaymentMethod(entity.getPaymentMethod());
        if (entity.getUnit() != null) {
            dto.setUnitId(entity.getUnit().getId());
            dto.setUnitNumber(entity.getUnit().getUnitNumber());
        }
        if (entity.getResident() != null) {
            dto.setResidentId(entity.getResident().getId());
            dto.setResidentName(entity.getResident().getName() + " " + entity.getResident().getLastName());
        }
        return dto;
    }

    default List<PaymentResponse> toResponseList(List<Payment> entities) {
        if (entities == null) return new ArrayList<>();
        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }

    default void updateEntityFromRequest(PaymentRequest dto, Payment entity) {
        if (dto == null || entity == null) return;
        if (dto.getAmount() != null) entity.setAmount(dto.getAmount());
        if (dto.getPaymentDate() != null) entity.setPaymentDate(dto.getPaymentDate());
        if (dto.getDueDate() != null) entity.setDueDate(dto.getDueDate());
        if (dto.getDescription() != null) entity.setDescription(dto.getDescription());
        if (dto.getPaymentMethod() != null) entity.setPaymentMethod(dto.getPaymentMethod());
        // Actualizar status
        if (dto.getPaymentDate() != null && dto.getDueDate() != null) {
            if (dto.getPaymentDate().isBefore(dto.getDueDate()) || dto.getPaymentDate().isEqual(dto.getDueDate())) {
                entity.setStatus(Payment.PaymentStatus.PAID);
            } else {
                entity.setStatus(Payment.PaymentStatus.OVERDUE);
            }
        }
    }
}

