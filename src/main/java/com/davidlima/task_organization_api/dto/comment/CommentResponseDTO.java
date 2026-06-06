package com.davidlima.task_organization_api.dto.comment;
import java.util.UUID;

public record CommentResponseDTO(
        UUID id,
        String comment,
        UUID authorId,
        String authorName
) {
}
