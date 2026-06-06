package com.davidlima.task_organization_api.dto.comment;
import java.time.LocalDateTime;
import java.util.UUID;

public record CommentResponseDTO(
        UUID id,
        String comment,
        UUID authorId,
        String authorName,
        UUID taskId,
        UUID projectId,
        LocalDateTime dateCreated
) {
}
