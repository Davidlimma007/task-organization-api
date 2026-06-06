package com.davidlima.task_organization_api.mapper;

import com.davidlima.task_organization_api.database.model.Project;
import com.davidlima.task_organization_api.dto.project.ProjectRequestDTO;
import com.davidlima.task_organization_api.dto.project.ProjectResponseDTO;
import com.davidlima.task_organization_api.enums.Status;

public interface IProjectMapper {

    default Project toProject(ProjectRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return Project.builder()
                .title(dto.title())
                .description(dto.description())
                .tagEntities(MapperUtils.tagReferences(dto.tagsId()))
                .author(MapperUtils.personReference(dto.authorId()))
                .status(Status.CREATED)
                .build();
    }

    default ProjectResponseDTO toProjectResponseDTO(Project project) {
        if (project == null) {
            return null;
        }
        return new ProjectResponseDTO(
                project.getId(),
                project.getTitle(),
                project.getDescription(),
                MapperUtils.taskIds(project.getTaskEntities()),
                MapperUtils.tagIds(project.getTagEntities()),
                MapperUtils.commentIds(project.getCommentEntities()),
                MapperUtils.personId(project.getAuthor()),
                MapperUtils.personFullName(project.getAuthor()),
                project.getStatus(),
                project.getDateCreated(),
                project.getDateEnd()
        );
    }
}
