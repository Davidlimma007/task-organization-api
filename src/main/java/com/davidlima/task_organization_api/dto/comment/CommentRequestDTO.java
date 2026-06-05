package com.davidlima.task_organization_api.dto.comment;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CommentRequestDTO(
        String comment,
        @NotBlank(message = "O Autor deve ser informado.")
        UUID authorId,
        @NotBlank(message = "A tarefa deve ser informada.")
        UUID taskId
) {
}
