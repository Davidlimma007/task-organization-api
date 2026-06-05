package com.davidlima.task_organization_api.dto.person;

import jakarta.validation.constraints.NotBlank;

public record PersonRequestDTO(
        @NotBlank(message = "O Nome deve ser informado.")
        String name,
        @NotBlank(message = "O Sobrenome deve ser informado.")
        String surname
) {
}
