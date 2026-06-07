package com.davidlima.task_organization_api.database.repository;

import com.davidlima.task_organization_api.database.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ITaskRepository extends JpaRepository<Task, UUID> {

    boolean existsByProjectId(UUID projectId);

    List<Task> findByAuthorId(UUID authorId);

    List<Task> findByProjectId(UUID projectId);
}
