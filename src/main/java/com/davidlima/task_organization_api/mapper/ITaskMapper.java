package com.davidlima.task_organization_api.mapper;

import com.davidlima.task_organization_api.database.model.Task;
import com.davidlima.task_organization_api.dto.task.TaskRequestDTO;
import com.davidlima.task_organization_api.dto.task.TaskResponseDTO;
import com.davidlima.task_organization_api.enums.Status;

public interface ITaskMapper {

    default Task toTask(TaskRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return Task.builder()
                .title(dto.title())
                .description(dto.description())
                .project(MapperUtils.projectReference(dto.projectId()))
                .commentEntities(MapperUtils.commentReferences(dto.commentsId()))
                .tagEntities(MapperUtils.tagReferences(dto.tagsId()))
                .author(MapperUtils.personReference(dto.authorId()))
                .status(Status.CREATED)
                .build();
    }

    default TaskResponseDTO toTaskResponseDTO(Task task) {
        if (task == null) {
            return null;
        }
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                MapperUtils.projectId(task.getProject()),
                MapperUtils.commentIds(task.getCommentEntities()),
                MapperUtils.tagIds(task.getTagEntities()),
                MapperUtils.personId(task.getAuthor()),
                MapperUtils.personFullName(task.getAuthor()),
                task.getStatus(),
                task.getDateCreated(),
                task.getDateEnd()
        );
    }
}
