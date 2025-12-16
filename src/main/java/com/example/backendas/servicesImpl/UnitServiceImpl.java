package com.example.backendas.servicesImpl;

import com.example.backendas.dtos.unit.UnitRequest;
import com.example.backendas.dtos.unit.UnitResponse;
import com.example.backendas.entities.Condominium;
import com.example.backendas.entities.Unit;
import com.example.backendas.exceptions.BusinessRuleException;
import com.example.backendas.exceptions.ResourceNotFoundException;
import com.example.backendas.mappers.UnitMapper;
import com.example.backendas.repositories.CondominiumRepository;
import com.example.backendas.repositories.UnitRepository;
import com.example.backendas.services.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitRepository repository;
    private final CondominiumRepository condominiumRepository;
    private final UnitMapper mapper;

    @Override
    public UnitResponse create(UnitRequest request) {
        if (repository.existsByUnitNumber(request.getUnitNumber())) {
            throw new BusinessRuleException("Ya existe una unidad con el número: " + request.getUnitNumber());
        }

        Condominium condominium = condominiumRepository.findById(request.getCondominiumId())
                .orElseThrow(() -> new ResourceNotFoundException("Condominio no encontrado con ID: " + request.getCondominiumId()));

        Unit entity = mapper.toEntity(request);
        entity.setCondominium(condominium);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public UnitResponse update(Long id, UnitRequest request) {
        Unit unit = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unidad no encontrada con ID: " + id));

        if (!unit.getUnitNumber().equals(request.getUnitNumber()) &&
            repository.existsByUnitNumber(request.getUnitNumber())) {
            throw new BusinessRuleException("Ya existe otra unidad con el número: " + request.getUnitNumber());
        }

        if (request.getCondominiumId() != null && !request.getCondominiumId().equals(unit.getCondominium().getId())) {
            Condominium condominium = condominiumRepository.findById(request.getCondominiumId())
                    .orElseThrow(() -> new ResourceNotFoundException("Condominio no encontrado con ID: " + request.getCondominiumId()));
            unit.setCondominium(condominium);
        }

        mapper.updateEntityFromRequest(request, unit);
        return mapper.toResponse(repository.save(unit));
    }

    @Override
    public List<UnitResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public UnitResponse findById(Long id) {
        Unit unit = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unidad no encontrada con ID: " + id));
        return mapper.toResponse(unit);
    }

    @Override
    public List<UnitResponse> findByCondominiumId(Long condominiumId) {
        return repository.findByCondominiumId(condominiumId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Unit unit = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unidad no encontrada con ID: " + id));
        repository.delete(unit);
    }
}

