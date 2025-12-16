package com.example.backendas.mappers;

import com.example.backendas.dtos.condominium.CondominiumRequest;
import com.example.backendas.dtos.condominium.CondominiumResponse;
import com.example.backendas.entities.Condominium;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CondominiumMapper {

    default Condominium toEntity(CondominiumRequest dto) {
        if (dto == null) return null;
        Condominium entity = new Condominium();
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        return entity;
    }

    default CondominiumResponse toResponse(Condominium entity) {
        if (entity == null) return null;
        CondominiumResponse dto = new CondominiumResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setTotalUnits(entity.getUnits() != null ? entity.getUnits().size() : 0);
        dto.setTotalAdministrators(entity.getAdministrators() != null ? entity.getAdministrators().size() : 0);
        return dto;
    }

    default List<CondominiumResponse> toResponseList(List<Condominium> entities) {
        if (entities == null) return new ArrayList<>();
        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }

    default void updateEntityFromRequest(CondominiumRequest dto, Condominium entity) {
        if (dto == null || entity == null) return;
        if (dto.getName() != null && !dto.getName().isBlank())
            entity.setName(dto.getName());
        if (dto.getAddress() != null && !dto.getAddress().isBlank())
            entity.setAddress(dto.getAddress());
        if (dto.getPhone() != null) entity.setPhone(dto.getPhone());
        if (dto.getEmail() != null && !dto.getEmail().isBlank())
            entity.setEmail(dto.getEmail());
    }
}

