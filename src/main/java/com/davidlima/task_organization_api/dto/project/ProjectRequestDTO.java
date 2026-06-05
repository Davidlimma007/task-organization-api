package com.davidlima.task_organization_api.dto.project;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public record ProjectRequestDTO(
        @NotBlank(message = "O nome do projeto deve ser informado")
        String name,
        List<UUID> tagsId
) {
}
