package com.davidlima.task_organization_api.dto.project;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public record ProjectRequestDTO(
        @NotBlank(message = "O titulo do projeto deve ser informado.")
        String title,
        @NotBlank(message = "A descrição do projeto deve ser informada.")
        String description,
        List<UUID> tagsId
) {
}
