package com.davidlima.task_organization_api.dto.person;

import java.util.UUID;

public record PersonResponseDTO(
        UUID id,
        UUID userId,
        String name,
        String surname
) {
}
