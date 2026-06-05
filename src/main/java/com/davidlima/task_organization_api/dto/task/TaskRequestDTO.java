package com.davidlima.task_organization_api.dto.task;

import java.util.List;
import java.util.UUID;

public record TaskRequestDTO(
        UUID projetcId,
        List<UUID> commentsId,
        List<UUID> tagsId,
        UUID author
) {
}
