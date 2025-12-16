package com.example.backendas.mappers;

import com.example.backendas.dtos.unit.UnitRequest;
import com.example.backendas.dtos.unit.UnitResponse;
import com.example.backendas.entities.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UnitMapper {

    default Unit toEntity(UnitRequest dto) {
        if (dto == null)
            return null;
        Unit entity = new Unit();
        entity.setUnitNumber(dto.getUnitNumber());
        if (dto.getFloor() != null) {
            entity.setFloor(Integer.parseInt(dto.getFloor()));
        }
        if (dto.getArea() != null) {
            entity.setArea(Double.parseDouble(dto.getArea()));
        }
        entity.setType(dto.getType());
        return entity;
    }

    default UnitResponse toResponse(Unit entity) {
        if (entity == null)
            return null;
        UnitResponse dto = new UnitResponse();
        dto.setId(entity.getId());
        dto.setUnitNumber(entity.getUnitNumber());
        dto.setFloor(entity.getFloor());
        dto.setArea(entity.getArea());
        dto.setType(entity.getType());
        if (entity.getCondominium() != null) {
            dto.setCondominiumId(entity.getCondominium().getId());
            dto.setCondominiumName(entity.getCondominium().getName());
        }
        if (entity.getResident() != null) {
            dto.setResidentId(entity.getResident().getId());
            dto.setResidentName(entity.getResident().getName() + " " + entity.getResident().getLastName());
            dto.setHasResident(true);
        } else {
            dto.setHasResident(false);
        }
        return dto;
    }

    default List<UnitResponse> toResponseList(List<Unit> entities) {
        if (entities == null)
            return new ArrayList<>();
        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }

    default void updateEntityFromRequest(UnitRequest dto, Unit entity) {
        if (dto == null || entity == null)
            return;
        if (dto.getUnitNumber() != null && !dto.getUnitNumber().isBlank())
            entity.setUnitNumber(dto.getUnitNumber());
        if (dto.getFloor() != null)
            entity.setFloor(Integer.parseInt(dto.getFloor()));
        if (dto.getArea() != null)
            entity.setArea(Double.parseDouble(dto.getArea()));
        if (dto.getType() != null)
            entity.setType(dto.getType());
    }
}
