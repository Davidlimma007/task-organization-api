package com.davidlima.task_organization_api.dto.comment;

import com.davidlima.task_organization_api.database.model.Person;
import com.davidlima.task_organization_api.database.model.Task;

public record CommentResponseDTO(
        String comment,
        Person author,
        Task task
) {
}
