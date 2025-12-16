package com.example.backendas.servicesImpl;

import com.example.backendas.dtos.incident.IncidentRequest;
import com.example.backendas.dtos.incident.IncidentResponse;
import com.example.backendas.entities.Incident;
import com.example.backendas.entities.Resident;
import com.example.backendas.entities.Unit;
import com.example.backendas.exceptions.BusinessRuleException;
import com.example.backendas.exceptions.ResourceNotFoundException;
import com.example.backendas.mappers.IncidentMapper;
import com.example.backendas.repositories.IncidentRepository;
import com.example.backendas.repositories.ResidentRepository;
import com.example.backendas.repositories.UnitRepository;
import com.example.backendas.services.IncidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidentServiceImpl implements IncidentService {

    private final IncidentRepository repository;
    private final UnitRepository unitRepository;
    private final ResidentRepository residentRepository;
    private final IncidentMapper mapper;

    @Override
    public IncidentResponse create(IncidentRequest request) {
        // Validar identidad si es residente
        org.springframework.security.core.Authentication authentication = 
            org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_RESIDENT"))) {
            
            String email = authentication.getName();
            Resident currentResident = residentRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Perfil de residente no encontrado para el usuario actual"));
            
            if (!currentResident.getId().equals(request.getResidentId())) {
                throw new BusinessRuleException("No puede crear incidencias para otros residentes");
            }
            
            // Si el residente tiene unidad, debe coincidir
            if (currentResident.getUnit() != null) {
                if (!currentResident.getUnit().getId().equals(request.getUnitId())) {
                    throw new BusinessRuleException("Solo puede crear incidencias para su unidad asignada");
                }
            }
        }

        Unit unit = unitRepository.findById(request.getUnitId())
                .orElseThrow(() -> new ResourceNotFoundException("Unidad no encontrada con ID: " + request.getUnitId()));

        Resident resident = residentRepository.findById(request.getResidentId())
                .orElseThrow(() -> new ResourceNotFoundException("Residente no encontrado con ID: " + request.getResidentId()));

        if (!resident.getUnit().getId().equals(unit.getId())) {
            throw new BusinessRuleException("El residente no pertenece a la unidad especificada");
        }

        Incident entity = mapper.toEntity(request);
        entity.setUnit(unit);
        entity.setResident(resident);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public IncidentResponse update(Long id, IncidentRequest request) {
        Incident incident = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incidencia no encontrada con ID: " + id));

        mapper.updateEntityFromRequest(request, incident);
        return mapper.toResponse(repository.save(incident));
    }

    @Override
    public List<IncidentResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public IncidentResponse findById(Long id) {
        Incident incident = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incidencia no encontrada con ID: " + id));
        return mapper.toResponse(incident);
    }

    @Override
    public List<IncidentResponse> findByResidentId(Long residentId) {
        return repository.findByResidentId(residentId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<IncidentResponse> findByUnitId(Long unitId) {
        return repository.findByUnitId(unitId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<IncidentResponse> findByStatus(Incident.IncidentStatus status) {
        return repository.findByStatus(status).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public IncidentResponse resolve(Long id, String resolutionNotes) {
        Incident incident = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incidencia no encontrada con ID: " + id));
        
        incident.setStatus(Incident.IncidentStatus.RESOLVED);
        incident.setResolvedDate(LocalDateTime.now());
        incident.setResolutionNotes(resolutionNotes);
        
        return mapper.toResponse(repository.save(incident));
    }

    @Override
    public void delete(Long id) {
        Incident incident = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incidencia no encontrada con ID: " + id));
        repository.delete(incident);
    }
}

