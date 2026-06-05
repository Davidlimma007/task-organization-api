package com.davidlima.task_organization_api.dto.role;

import jakarta.validation.constraints.NotBlank;

public record RoleRequestDTO(
        @NotBlank(message = "A nome do Papel deve ser informado.")
        String name
) {
}
