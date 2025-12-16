package com.example.backendas.mappers;

import com.example.backendas.dtos.reservation.ReservationRequest;
import com.example.backendas.dtos.reservation.ReservationResponse;
import com.example.backendas.entities.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {

    default Reservation toEntity(ReservationRequest dto) {
        if (dto == null) return null;
        Reservation entity = new Reservation();
        entity.setAreaName(dto.getAreaName());
        entity.setStartDateTime(dto.getStartDateTime());
        entity.setEndDateTime(dto.getEndDateTime());
        entity.setDescription(dto.getDescription());
        entity.setCost(dto.getCost());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : Reservation.ReservationStatus.PENDING);
        return entity;
    }

    default ReservationResponse toResponse(Reservation entity) {
        if (entity == null) return null;
        ReservationResponse dto = new ReservationResponse();
        dto.setId(entity.getId());
        dto.setAreaName(entity.getAreaName());
        dto.setStartDateTime(entity.getStartDateTime());
        dto.setEndDateTime(entity.getEndDateTime());
        dto.setStatus(entity.getStatus());
        dto.setDescription(entity.getDescription());
        dto.setCost(entity.getCost());
        if (entity.getResident() != null) {
            dto.setResidentId(entity.getResident().getId());
            dto.setResidentName(entity.getResident().getName() + " " + entity.getResident().getLastName());
            if (entity.getResident().getUnit() != null) {
                dto.setUnitNumber(entity.getResident().getUnit().getUnitNumber());
            }
        }
        return dto;
    }

    default List<ReservationResponse> toResponseList(List<Reservation> entities) {
        if (entities == null) return new ArrayList<>();
        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }

    default void updateEntityFromRequest(ReservationRequest dto, Reservation entity) {
        if (dto == null || entity == null) return;
        if (dto.getAreaName() != null && !dto.getAreaName().isBlank())
            entity.setAreaName(dto.getAreaName());
        if (dto.getStartDateTime() != null) entity.setStartDateTime(dto.getStartDateTime());
        if (dto.getEndDateTime() != null) entity.setEndDateTime(dto.getEndDateTime());
        if (dto.getDescription() != null) entity.setDescription(dto.getDescription());
        if (dto.getCost() != null) entity.setCost(dto.getCost());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());
    }
}

