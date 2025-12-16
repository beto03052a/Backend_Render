package com.example.backendas.servicesImpl;

import com.example.backendas.dtos.administrator.AdministratorRequest;
import com.example.backendas.dtos.administrator.AdministratorResponse;
import com.example.backendas.entities.Administrator;
import com.example.backendas.entities.Condominium;
import com.example.backendas.exceptions.BusinessRuleException;
import com.example.backendas.exceptions.ResourceNotFoundException;
import com.example.backendas.mappers.AdministratorMapper;
import com.example.backendas.repositories.AdministratorRepository;
import com.example.backendas.repositories.CondominiumRepository;
import com.example.backendas.services.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {

    private final AdministratorRepository repository;
    private final CondominiumRepository condominiumRepository;
    private final AdministratorMapper mapper;

    @Override
    public AdministratorResponse create(AdministratorRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new BusinessRuleException("Ya existe un administrador con el email: " + request.getEmail());
        }

        Condominium condominium = condominiumRepository.findById(request.getCondominiumId())
                .orElseThrow(() -> new ResourceNotFoundException("Condominio no encontrado con ID: " + request.getCondominiumId()));

        Administrator entity = mapper.toEntity(request);
        entity.setCondominium(condominium);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public AdministratorResponse update(Long id, AdministratorRequest request) {
        Administrator administrator = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Administrador no encontrado con ID: " + id));

        if (!administrator.getEmail().equals(request.getEmail()) &&
            repository.existsByEmail(request.getEmail())) {
            throw new BusinessRuleException("Ya existe otro administrador con el email: " + request.getEmail());
        }

        if (request.getCondominiumId() != null && !request.getCondominiumId().equals(administrator.getCondominium().getId())) {
            Condominium condominium = condominiumRepository.findById(request.getCondominiumId())
                    .orElseThrow(() -> new ResourceNotFoundException("Condominio no encontrado con ID: " + request.getCondominiumId()));
            administrator.setCondominium(condominium);
        }

        mapper.updateEntityFromRequest(request, administrator);
        return mapper.toResponse(repository.save(administrator));
    }

    @Override
    public List<AdministratorResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public AdministratorResponse findById(Long id) {
        Administrator administrator = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Administrador no encontrado con ID: " + id));
        return mapper.toResponse(administrator);
    }

    @Override
    public List<AdministratorResponse> findByCondominiumId(Long condominiumId) {
        return repository.findByCondominiumId(condominiumId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Administrator administrator = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Administrador no encontrado con ID: " + id));
        repository.delete(administrator);
    }
}

