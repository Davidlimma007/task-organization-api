package com.davidlima.task_organization_api.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CommentRequestDTO(
        @NotBlank(message = "O comentário deve ser informado.")
        String comment,
        @NotNull(message = "O autor deve ser informado.")
        UUID authorId,
        UUID taskId,
        UUID projectId
) {
}
