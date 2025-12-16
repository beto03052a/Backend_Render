package com.example.backendas.mappers;

import com.example.backendas.dtos.administrator.AdministratorRequest;
import com.example.backendas.dtos.administrator.AdministratorResponse;
import com.example.backendas.entities.Administrator;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdministratorMapper {

    default Administrator toEntity(AdministratorRequest dto) {
        if (dto == null) return null;
        Administrator entity = new Administrator();
        entity.setName(dto.getName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPosition(dto.getPosition());
        return entity;
    }

    default AdministratorResponse toResponse(Administrator entity) {
        if (entity == null) return null;
        AdministratorResponse dto = new AdministratorResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setPosition(entity.getPosition());
        if (entity.getCondominium() != null) {
            dto.setCondominiumId(entity.getCondominium().getId());
            dto.setCondominiumName(entity.getCondominium().getName());
        }
        return dto;
    }

    default List<AdministratorResponse> toResponseList(List<Administrator> entities) {
        if (entities == null) return new ArrayList<>();
        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }

    default void updateEntityFromRequest(AdministratorRequest dto, Administrator entity) {
        if (dto == null || entity == null) return;
        if (dto.getName() != null && !dto.getName().isBlank())
            entity.setName(dto.getName());
        if (dto.getLastName() != null && !dto.getLastName().isBlank())
            entity.setLastName(dto.getLastName());
        if (dto.getEmail() != null && !dto.getEmail().isBlank())
            entity.setEmail(dto.getEmail());
        if (dto.getPhone() != null) entity.setPhone(dto.getPhone());
        if (dto.getPosition() != null && !dto.getPosition().isBlank())
            entity.setPosition(dto.getPosition());
    }
}

