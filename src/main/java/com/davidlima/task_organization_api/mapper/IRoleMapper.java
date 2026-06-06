package com.davidlima.task_organization_api.mapper;

import com.davidlima.task_organization_api.database.model.Roles;
import com.davidlima.task_organization_api.dto.role.RoleRequestDTO;
import com.davidlima.task_organization_api.dto.role.RoleResponseDTO;

import javax.management.relation.Role;

public interface IRoleMapper {

    default Roles toRole(RoleRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return Roles.builder()
                .name(dto.name())
                .build();
    }

    default RoleResponseDTO toRoleResponseDTO(Roles role) {
        if (role == null) {
            return null;
        }
        return new RoleResponseDTO(role.getId(), role.getName());
    }
}
