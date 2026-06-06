package com.davidlima.task_organization_api.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record ProjectRequestDTO(
        @NotBlank(message = "O título do projeto deve ser informado.")
        String title,
        @NotBlank(message = "A descrição do projeto deve ser informada.")
        String description,
        List<UUID> tagsId,
        @NotNull(message = "O autor deve ser informado.")
        UUID authorId
) {
}
