package com.davidlima.task_organization_api.dto.task;

import com.davidlima.task_organization_api.database.model.Comment;
import com.davidlima.task_organization_api.database.model.Person;
import com.davidlima.task_organization_api.database.model.Project;
import com.davidlima.task_organization_api.database.model.Tag;
import com.davidlima.task_organization_api.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public record TaskResponseDTO(
        Project project,
        List<Comment> comment,
        List<Tag> tags,
        Person author,
        Status status,
        LocalDateTime dateCreated
) {
}
