package com.davidlima.task_organization_api.database.repository;

import com.davidlima.task_organization_api.database.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ICommentRepository extends JpaRepository<Comment, UUID> {

    List<Comment> findByAuthorId(UUID authorId);

    List<Comment> findByProjectId(UUID projectId);

    List<Comment> findByTaskId(UUID taskId);
}
