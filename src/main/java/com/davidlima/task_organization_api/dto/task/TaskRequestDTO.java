package com.davidlima.task_organization_api.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record TaskRequestDTO(
        @NotBlank(message = "O título deve ser informado.")
        String title,
        @NotBlank(message = "A descrição deve ser informada.")
        String description,
        @NotNull(message = "O projeto deve ser informado.")
        UUID projectId,
        List<UUID> commentsId,
        List<UUID> tagsId,
        @NotNull(message = "O autor deve ser informado.")
        UUID authorId
) {
}
