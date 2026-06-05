package com.davidlima.task_organization_api.dto.tag;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record TagRequestDTO(
        @NotBlank(message = "O noma da Tag deve ser informado.")
        String name,
        UUID authorId
) {
}
