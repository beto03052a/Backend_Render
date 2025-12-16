package com.example.backendas.servicesImpl;

import com.example.backendas.dtos.condominium.CondominiumRequest;
import com.example.backendas.dtos.condominium.CondominiumResponse;
import com.example.backendas.entities.Condominium;
import com.example.backendas.exceptions.BusinessRuleException;
import com.example.backendas.exceptions.ResourceNotFoundException;
import com.example.backendas.mappers.CondominiumMapper;
import com.example.backendas.repositories.CondominiumRepository;
import com.example.backendas.services.CondominiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CondominiumServiceImpl implements CondominiumService {

    private final CondominiumRepository repository;
    private final CondominiumMapper mapper;

    @Override
    public CondominiumResponse create(CondominiumRequest request) {
        if (request.getEmail() != null && !request.getEmail().isBlank() && 
            repository.existsByEmail(request.getEmail())) {
            throw new BusinessRuleException("Ya existe un condominio con el email: " + request.getEmail());
        }

        Condominium entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public CondominiumResponse update(Long id, CondominiumRequest request) {
        Condominium condominium = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Condominio no encontrado con ID: " + id));

        if (request.getEmail() != null && !request.getEmail().isBlank() &&
            !condominium.getEmail().equals(request.getEmail()) &&
            repository.existsByEmail(request.getEmail())) {
            throw new BusinessRuleException("Ya existe otro condominio con el email: " + request.getEmail());
        }

        mapper.updateEntityFromRequest(request, condominium);
        return mapper.toResponse(repository.save(condominium));
    }

    @Override
    public List<CondominiumResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public CondominiumResponse findById(Long id) {
        Condominium condominium = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Condominio no encontrado con ID: " + id));
        return mapper.toResponse(condominium);
    }

    @Override
    public void delete(Long id) {
        Condominium condominium = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Condominio no encontrado con ID: " + id));
        repository.delete(condominium);
    }
}

