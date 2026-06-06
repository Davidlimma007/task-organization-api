package com.davidlima.task_organization_api.dto.person;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PersonRequestDTO(
        @NotNull(message = "O usuário deve ser informado.")
        UUID userId,
        @NotBlank(message = "O nome deve ser informado.")
        String name,
        @NotBlank(message = "O sobrenome deve ser informado.")
        String surname
) {
}
