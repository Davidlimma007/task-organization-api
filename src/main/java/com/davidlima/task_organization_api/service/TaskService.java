package com.davidlima.task_organization_api.service;

import com.davidlima.task_organization_api.database.model.Person;
import com.davidlima.task_organization_api.database.model.Project;
import com.davidlima.task_organization_api.database.model.Task;
import com.davidlima.task_organization_api.database.repository.ITaskRepository;
import com.davidlima.task_organization_api.dto.task.TaskRequestDTO;
import com.davidlima.task_organization_api.dto.task.TaskResponseDTO;
import com.davidlima.task_organization_api.enums.Status;
import com.davidlima.task_organization_api.exception.BusinessRuleException;
import com.davidlima.task_organization_api.exception.ResourceNotFoundException;
import com.davidlima.task_organization_api.mapper.ITaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final ITaskRepository taskRepository;
    private final ProjectService projectService;
    private final PersonService personService;
    private final TagService tagService;
    private final ITaskMapper taskMapper;

    public TaskResponseDTO create(TaskRequestDTO dto) {
        Project project = projectService.findEntityById(dto.projectId());
        if (Status.COMPLETED.equals(project.getStatus()) || Status.CANCELED.equals(project.getStatus())) {
            throw new BusinessRuleException("Não é possível criar tarefas para um projeto finalizado.");
        }
        Person author = personService.findEntityById(dto.author());
        Task task = taskMapper.toTask(dto);
        task.setProject(project);
        task.setAuthor(author);
        task.setTagEntities(tagService.findEntitiesByIds(dto.tagsId()));
        task.setStatus(Status.CREATED);
        return taskMapper.toTaskResponseDTO(taskRepository.save(task));
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> findAll() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toTaskResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> findByProject(UUID projectId) {
        return taskRepository.findByProjectId(projectId).stream()
                .map(taskMapper::toTaskResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> findByAuthor(UUID authorId) {
        return taskRepository.findByAuthorId(authorId).stream()
                .map(taskMapper::toTaskResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public TaskResponseDTO findById(UUID id) {
        return taskMapper.toTaskResponseDTO(findEntityById(id));
    }

    public TaskResponseDTO update(UUID id, TaskRequestDTO dto) {
        Task task = findEntityById(id);
        ensureTaskIsEditable(task);
        Project project = projectService.findEntityById(dto.projectId());
        Person author = personService.findEntityById(dto.author());
        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setProject(project);
        task.setAuthor(author);
        task.setTagEntities(tagService.findEntitiesByIds(dto.tagsId()));
        return taskMapper.toTaskResponseDTO(taskRepository.save(task));
    }

    public TaskResponseDTO updateStatus(UUID id, Status status) {
        Task task = findEntityById(id);
        validateStatusTransition(task.getStatus(), status);
        task.setStatus(status);
        task.setDateEnd(isFinalStatus(status) ? LocalDateTime.now() : null);
        return taskMapper.toTaskResponseDTO(taskRepository.save(task));
    }

    public void delete(UUID id) {
        Task task = findEntityById(id);
        taskRepository.delete(task);
    }

    public Task findEntityById(UUID id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada."));
    }

    private void ensureTaskIsEditable(Task task) {
        if (isFinalStatus(task.getStatus())) {
            throw new BusinessRuleException("Tarefas concluídas ou canceladas não podem ser editadas.");
        }
    }

    private void validateStatusTransition(Status currentStatus, Status newStatus) {
        if (newStatus == null) {
            throw new BusinessRuleException("O status da tarefa deve ser informado.");
        }
        if (isFinalStatus(currentStatus) && !currentStatus.equals(newStatus)) {
            throw new BusinessRuleException("Não é possível alterar o status de uma tarefa finalizada.");
        }
        if (Status.CREATED.equals(currentStatus) && Status.COMPLETED.equals(newStatus)) {
            throw new BusinessRuleException("Uma tarefa criada deve ser iniciada antes de ser concluída.");
        }
    }

    private boolean isFinalStatus(Status status) {
        return Status.COMPLETED.equals(status) || Status.CANCELED.equals(status);
    }
}
