package com.davidlima.task_organization_api.dto.role;

import java.util.UUID;

public record RoleResponseDTO(
        UUID id,
        String name
) {
}
