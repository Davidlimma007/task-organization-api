package com.davidlima.task_organization_api.mapper;

import com.davidlima.task_organization_api.database.model.Project;
import com.davidlima.task_organization_api.dto.project.ProjectRequestDTO;
import com.davidlima.task_organization_api.dto.project.ProjectResponseDTO;

public interface IProjectMapper {

    Project toProject(ProjectRequestDTO dto);

    ProjectResponseDTO toProjectResponseDTO(Project project);
}
