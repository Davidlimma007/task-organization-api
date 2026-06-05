package com.davidlima.task_organization_api.dto.project;

import com.davidlima.task_organization_api.database.model.Person;
import com.davidlima.task_organization_api.database.model.Tag;
import com.davidlima.task_organization_api.database.model.Task;
import com.davidlima.task_organization_api.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public record ProjectResponseDTO(
        String name,
        List<Task> tasks,
        List<Tag> tags,
        Person author,
        Status status,
        LocalDateTime dateCreated
) {
}
