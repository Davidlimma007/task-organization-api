package com.davidlima.task_organization_api.dto.task;

import com.davidlima.task_organization_api.enums.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TaskResponseDTO(
        UUID id,
        String title,
        String description,
        UUID projectId,
        List<UUID> commentsId,
        List<UUID> tagsId,
        UUID authorId,
        String authorName,
        Status status,
        LocalDateTime dateCreated,
        LocalDateTime dateEnd
) {
}
