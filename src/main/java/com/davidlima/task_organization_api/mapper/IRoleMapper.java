package com.davidlima.task_organization_api.mapper;

import com.davidlima.task_organization_api.dto.role.RoleRequestDTO;
import com.davidlima.task_organization_api.dto.role.RoleResponseDTO;

import javax.management.relation.Role;

public interface IRoleMapper {

    Role toRole(RoleRequestDTO dto);

    RoleResponseDTO toRoleResponseDTO(Role role);
}
