package com.example.backendas.servicesImpl;

import com.example.backendas.dtos.resident.ResidentRequest;
import com.example.backendas.dtos.resident.ResidentResponse;
import com.example.backendas.entities.Resident;
import com.example.backendas.entities.Unit;
import com.example.backendas.exceptions.BusinessRuleException;
import com.example.backendas.exceptions.ResourceNotFoundException;
import com.example.backendas.mappers.ResidentMapper;
import com.example.backendas.repositories.ResidentRepository;
import com.example.backendas.repositories.UnitRepository;
import com.example.backendas.services.ResidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ResidentServiceImpl implements ResidentService {

    private final ResidentRepository repository;
    private final UnitRepository unitRepository;
    private final ResidentMapper mapper;

    @Override
    public ResidentResponse create(ResidentRequest request) {
        // 🔒 Validar si el usuario actual es RESIDENT y ya tiene perfil
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_RESIDENT"))) {
            
            String currentUserEmail = authentication.getName(); // El username es el email

            // 1. Verificar si ya existe un residente con este email
            if (repository.existsByEmail(currentUserEmail)) {
                throw new BusinessRuleException("Ya tienes un perfil de residente creado. No puedes crear otro.");
            }

            // 2. Forzar que el email del residente sea el del usuario autenticado
            request.setEmail(currentUserEmail);
        }

        if (repository.existsByEmail(request.getEmail())) {
            throw new BusinessRuleException("Ya existe un residente con el email: " + request.getEmail());
        }

        if (repository.existsByIdentificationNumber(request.getIdentificationNumber())) {
            throw new BusinessRuleException("Ya existe un residente con el número de identificación: " + request.getIdentificationNumber());
        }

        Resident entity = mapper.toEntity(request);

        if (request.getUnitId() != null) {
            Unit unit = unitRepository.findById(request.getUnitId())
                    .orElseThrow(() -> new ResourceNotFoundException("Unidad no encontrada con ID: " + request.getUnitId()));
            
            if (unit.getResident() != null) {
                throw new BusinessRuleException("La unidad ya tiene un residente asignado");
            }
            
            entity.setUnit(unit);
        }

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public ResidentResponse update(Long id, ResidentRequest request) {
        Resident resident = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Residente no encontrado con ID: " + id));

        if (!resident.getEmail().equals(request.getEmail()) &&
            repository.existsByEmail(request.getEmail())) {
            throw new BusinessRuleException("Ya existe otro residente con el email: " + request.getEmail());
        }

        if (!resident.getIdentificationNumber().equals(request.getIdentificationNumber()) &&
            repository.existsByIdentificationNumber(request.getIdentificationNumber())) {
            throw new BusinessRuleException("Ya existe otro residente con el número de identificación: " + request.getIdentificationNumber());
        }

        mapper.updateEntityFromRequest(request, resident);
        
        // 🏠 Manejar asignación de unidad
        if (request.getUnitId() != null) {
            Unit newUnit = unitRepository.findById(request.getUnitId())
                    .orElseThrow(() -> new ResourceNotFoundException("Unidad no encontrada con ID: " + request.getUnitId()));
            
            // Verificar si la unidad ya tiene otro residente asignado
            if (newUnit.getResident() != null && !newUnit.getResident().getId().equals(id)) {
                throw new BusinessRuleException("La unidad ya tiene otro residente asignado");
            }
            
            // Si el residente tenía una unidad anterior diferente, desasignarla
            if (resident.getUnit() != null && !resident.getUnit().getId().equals(request.getUnitId())) {
                Unit oldUnit = resident.getUnit();
                oldUnit.setResident(null);
                unitRepository.save(oldUnit);
            }
            
            resident.setUnit(newUnit);
        } else {
            // Si unitId es null, desasignar la unidad actual
            if (resident.getUnit() != null) {
                Unit oldUnit = resident.getUnit();
                oldUnit.setResident(null);
                unitRepository.save(oldUnit);
                resident.setUnit(null);
            }
        }
        
        return mapper.toResponse(repository.save(resident));
    }

    @Override
    public List<ResidentResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public ResidentResponse findById(Long id) {
        Resident resident = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Residente no encontrado con ID: " + id));
        return mapper.toResponse(resident);
    }

    @Override
    public ResidentResponse assignToUnit(Long residentId, Long unitId) {
        Resident resident = repository.findById(residentId)
                .orElseThrow(() -> new ResourceNotFoundException("Residente no encontrado con ID: " + residentId));

        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new ResourceNotFoundException("Unidad no encontrada con ID: " + unitId));

        if (unit.getResident() != null && !unit.getResident().getId().equals(residentId)) {
            throw new BusinessRuleException("La unidad ya tiene otro residente asignado");
        }

        // Si el residente ya tenía una unidad asignada, desasignarla
        if (resident.getUnit() != null && !resident.getUnit().getId().equals(unitId)) {
            Unit oldUnit = resident.getUnit();
            oldUnit.setResident(null);
            unitRepository.save(oldUnit);
        }

        resident.setUnit(unit);
        return mapper.toResponse(repository.save(resident));
    }

    @Override
    public void delete(Long id) {
        Resident resident = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Residente no encontrado con ID: " + id));
        repository.delete(resident);
    }
}

