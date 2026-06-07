package com.davidlima.task_organization_api.dto.project;

import com.davidlima.task_organization_api.enums.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProjectResponseDTO(
        UUID id,
        String title,
        String description,
        List<UUID> tasksId,
        List<UUID> tagsId,
        List<UUID> commentsId,
        UUID authorId,
        String authorName,
        Status status,
        LocalDateTime dateCreated
) {
}
