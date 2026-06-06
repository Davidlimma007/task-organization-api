package com.davidlima.task_organization_api.dto.user;

import java.util.Set;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        UUID personId,
        String email,
        Boolean active,
        Set<UUID> rolesId
) {
}
