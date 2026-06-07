package com.davidlima.task_organization_api.service;

import com.davidlima.task_organization_api.database.model.Roles;
import com.davidlima.task_organization_api.database.repository.IRolesRepository;
import com.davidlima.task_organization_api.database.repository.IUserRepository;
import com.davidlima.task_organization_api.dto.role.RoleRequestDTO;
import com.davidlima.task_organization_api.dto.role.RoleResponseDTO;
import com.davidlima.task_organization_api.exception.BusinessRuleException;
import com.davidlima.task_organization_api.exception.ResourceNotFoundException;
import com.davidlima.task_organization_api.mapper.IRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {

    private final IRolesRepository rolesRepository;
    private final IUserRepository userRepository;
    private final IRoleMapper roleMapper;

    public RoleResponseDTO create(RoleRequestDTO dto) {
        validateUniqueName(dto.name(), null);
        Roles role = roleMapper.toRole(dto);
        role.setName(normalizeName(dto.name()));
        return roleMapper.toRoleResponseDTO(rolesRepository.save(role));
    }

    @Transactional(readOnly = true)
    public List<RoleResponseDTO> findAll() {
        return rolesRepository.findAll().stream()
                .map(roleMapper::toRoleResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public RoleResponseDTO findById(UUID id) {
        return roleMapper.toRoleResponseDTO(findEntityById(id));
    }

    public RoleResponseDTO update(UUID id, RoleRequestDTO dto) {
        Roles role = findEntityById(id);
        validateUniqueName(dto.name(), id);
        role.setName(normalizeName(dto.name()));
        return roleMapper.toRoleResponseDTO(rolesRepository.save(role));
    }

    public void delete(UUID id) {
        Roles role = findEntityById(id);
        if (userRepository.existsByRoles_Id(id)) {
            throw new BusinessRuleException("Não é possível remover um papel vinculado a usuários.");
        }
        rolesRepository.delete(role);
    }

    public Roles findEntityById(UUID id) {
        return rolesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Papel não encontrado."));
    }

    private void validateUniqueName(String name, UUID currentId) {
        rolesRepository.findByNameIgnoreCase(normalizeName(name))
                .filter(role -> currentId == null || !role.getId().equals(currentId))
                .ifPresent(role -> {
                    throw new BusinessRuleException("Já existe um papel cadastrado com este nome.");
                });
    }

    private String normalizeName(String name) {
        return name == null ? null : name.trim().toUpperCase();
    }
}
