package com.davidlima.task_organization_api.dto.user;

import com.davidlima.task_organization_api.database.model.Roles;
import com.davidlima.task_organization_api.dto.person.PersonResponseDTO;

import java.util.Set;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        PersonResponseDTO person,
        String email,
        Boolean active,
        Set<Roles> roles
) {
}
