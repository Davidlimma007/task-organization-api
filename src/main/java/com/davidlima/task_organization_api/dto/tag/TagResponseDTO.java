package com.davidlima.task_organization_api.dto.tag;

import java.time.LocalDateTime;
import java.util.UUID;

public record TagResponseDTO(
        UUID id,
        String name,
        UUID author,
        String authorName,
        LocalDateTime dateCreated
) {
}
