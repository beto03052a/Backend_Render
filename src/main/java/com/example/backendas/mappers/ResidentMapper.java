package com.example.backendas.mappers;

import com.example.backendas.dtos.resident.ResidentRequest;
import com.example.backendas.dtos.resident.ResidentResponse;
import com.example.backendas.entities.Resident;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResidentMapper {

    default Resident toEntity(ResidentRequest dto) {
        if (dto == null) return null;
        Resident entity = new Resident();
        entity.setName(dto.getName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setIdentificationNumber(dto.getIdentificationNumber());
        return entity;
    }

    default ResidentResponse toResponse(Resident entity) {
        if (entity == null) return null;
        ResidentResponse dto = new ResidentResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setIdentificationNumber(entity.getIdentificationNumber());
        if (entity.getUnit() != null) {
            dto.setUnitId(entity.getUnit().getId());
            dto.setUnitNumber(entity.getUnit().getUnitNumber());
            if (entity.getUnit().getCondominium() != null) {
                dto.setCondominiumName(entity.getUnit().getCondominium().getName());
            }
        }
        dto.setTotalPayments(entity.getPayments() != null ? entity.getPayments().size() : 0);
        dto.setTotalReservations(entity.getReservations() != null ? entity.getReservations().size() : 0);
        dto.setTotalIncidents(entity.getIncidents() != null ? entity.getIncidents().size() : 0);
        return dto;
    }

    default List<ResidentResponse> toResponseList(List<Resident> entities) {
        if (entities == null) return new ArrayList<>();
        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }

    default void updateEntityFromRequest(ResidentRequest dto, Resident entity) {
        if (dto == null || entity == null) return;
        if (dto.getName() != null && !dto.getName().isBlank())
            entity.setName(dto.getName());
        if (dto.getLastName() != null && !dto.getLastName().isBlank())
            entity.setLastName(dto.getLastName());
        if (dto.getEmail() != null && !dto.getEmail().isBlank())
            entity.setEmail(dto.getEmail());
        if (dto.getPhone() != null) entity.setPhone(dto.getPhone());
        if (dto.getIdentificationNumber() != null && !dto.getIdentificationNumber().isBlank())
            entity.setIdentificationNumber(dto.getIdentificationNumber());
    }
}

