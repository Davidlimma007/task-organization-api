package com.davidlima.task_organization_api.database.repository;

import com.davidlima.task_organization_api.database.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IProjectRepository extends JpaRepository<Project, UUID> {

    List<Project> findByAuthorId(UUID authorId);
}
