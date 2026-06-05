package com.davidlima.task_organization_api.dto.tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TagRequestDTO(
        @NotBlank(message = "O noma da Tag deve ser informado.")
        String name,
        @NotNull(message = "O autor deve ser informado.")
        UUID authorId
) {
}
