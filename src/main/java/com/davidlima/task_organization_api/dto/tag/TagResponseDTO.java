package com.davidlima.task_organization_api.dto.tag;

import com.davidlima.task_organization_api.database.model.Person;

import java.time.LocalDateTime;

public record TagResponseDTO(
        String name,
        Person author,
        LocalDateTime dateCreated
) {
}
