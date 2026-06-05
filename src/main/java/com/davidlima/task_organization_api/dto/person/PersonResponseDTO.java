package com.davidlima.task_organization_api.dto.person;

import java.util.UUID;

public record PersonResponseDTO(
        UUID id,
        String name,
        String surname
) {
}
