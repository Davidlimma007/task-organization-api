package com.davidlima.task_organization_api.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRequestDTO(
        @NotBlank(message = "O Email deve ser informado.")
        @Pattern(
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "O e-mail inserido é inválido. Certifique-se de incluir o '@' e um domínio válido (ex: usuário@gmail.com)."
        )
        String email,
        @NotBlank(message = "A Senha deve ser informada.")
        String password
) {
}
