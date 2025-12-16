package com.example.backendas.mappers;

import com.example.backendas.dtos.incident.IncidentRequest;
import com.example.backendas.dtos.incident.IncidentResponse;
import com.example.backendas.entities.Incident;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IncidentMapper {

    default Incident toEntity(IncidentRequest dto) {
        if (dto == null) return null;
        Incident entity = new Incident();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setType(dto.getType());
        entity.setReportedDate(java.time.LocalDateTime.now());
        entity.setStatus(Incident.IncidentStatus.OPEN);
        return entity;
    }

    default IncidentResponse toResponse(Incident entity) {
        if (entity == null) return null;
        IncidentResponse dto = new IncidentResponse();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setReportedDate(entity.getReportedDate());
        dto.setResolvedDate(entity.getResolvedDate());
        dto.setStatus(entity.getStatus());
        dto.setType(entity.getType());
        dto.setResolutionNotes(entity.getResolutionNotes());
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

    default List<IncidentResponse> toResponseList(List<Incident> entities) {
        if (entities == null) return new ArrayList<>();
        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }

    default void updateEntityFromRequest(IncidentRequest dto, Incident entity) {
        if (dto == null || entity == null) return;
        if (dto.getTitle() != null && !dto.getTitle().isBlank())
            entity.setTitle(dto.getTitle());
        if (dto.getDescription() != null && !dto.getDescription().isBlank())
            entity.setDescription(dto.getDescription());
        if (dto.getType() != null) entity.setType(dto.getType());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());
    }
}

