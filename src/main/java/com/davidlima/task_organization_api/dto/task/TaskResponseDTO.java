package com.davidlima.task_organization_api.dto.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TaskResponseDTO(
        UUID id,
        String title,
        String description,
        UUID projectId,
        List<UUID> commentId,
        List<UUID> tagsId,
        UUID authorId,
        String authorName,
        UUID statusId,
        LocalDateTime dateCreated
) {
}
