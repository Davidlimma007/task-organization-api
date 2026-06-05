package com.davidlima.task_organization_api.mapper;

import com.davidlima.task_organization_api.database.model.Task;
import com.davidlima.task_organization_api.dto.task.TaskRequestDTO;
import com.davidlima.task_organization_api.dto.task.TaskResponseDTO;

public interface ITaskMapper {

    Task toTask(TaskRequestDTO dto);

    TaskResponseDTO toTaskResponseDTO(Task task);
}
