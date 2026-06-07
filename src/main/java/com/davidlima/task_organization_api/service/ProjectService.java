package com.davidlima.task_organization_api.service;

import com.davidlima.task_organization_api.database.model.Person;
import com.davidlima.task_organization_api.database.model.Project;
import com.davidlima.task_organization_api.database.repository.IProjectRepository;
import com.davidlima.task_organization_api.database.repository.ITaskRepository;
import com.davidlima.task_organization_api.dto.project.ProjectRequestDTO;
import com.davidlima.task_organization_api.dto.project.ProjectResponseDTO;
import com.davidlima.task_organization_api.enums.Status;
import com.davidlima.task_organization_api.exception.BusinessRuleException;
import com.davidlima.task_organization_api.exception.ResourceNotFoundException;
import com.davidlima.task_organization_api.mapper.IProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {

    private final IProjectRepository projectRepository;
    private final ITaskRepository taskRepository;
    private final PersonService personService;
    private final TagService tagService;
    private final IProjectMapper projectMapper;

    public ProjectResponseDTO create(ProjectRequestDTO dto, UUID authorId) {
        Person author = personService.findEntityById(authorId);
        Project project = projectMapper.toProject(dto);
        project.setAuthor(author);
        project.setTagEntities(tagService.findEntitiesByIds(dto.tagsId()));
        project.setStatus(Status.CREATED);
        return projectMapper.toProjectResponseDTO(projectRepository.save(project));
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> findAll() {
        return projectRepository.findAll().stream()
                .map(projectMapper::toProjectResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> findByAuthor(UUID authorId) {
        return projectRepository.findByAuthorId(authorId).stream()
                .map(projectMapper::toProjectResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProjectResponseDTO findById(UUID id) {
        return projectMapper.toProjectResponseDTO(findEntityById(id));
    }

    public ProjectResponseDTO update(UUID id, ProjectRequestDTO dto) {
        Project project = findEntityById(id);
        ensureProjectIsEditable(project);
        project.setTitle(dto.title());
        project.setDescription(dto.description());
        project.setTagEntities(tagService.findEntitiesByIds(dto.tagsId()));
        return projectMapper.toProjectResponseDTO(projectRepository.save(project));
    }

    public ProjectResponseDTO updateStatus(UUID id, Status status) {
        Project project = findEntityById(id);
        validateStatusTransition(project.getStatus(), status);
        project.setStatus(status);
        project.setDateEnd(isFinalStatus(status) ? LocalDateTime.now() : null);
        return projectMapper.toProjectResponseDTO(projectRepository.save(project));
    }

    public void delete(UUID id) {
        Project project = findEntityById(id);
        if (taskRepository.existsByProjectId(id)) {
            throw new BusinessRuleException("Não é possível remover um projeto que possui tarefas vinculadas.");
        }
        projectRepository.delete(project);
    }

    public Project findEntityById(UUID id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado."));
    }

    private void ensureProjectIsEditable(Project project) {
        if (isFinalStatus(project.getStatus())) {
            throw new BusinessRuleException("Projetos concluídos ou cancelados não podem ser editados.");
        }
    }

    private void validateStatusTransition(Status currentStatus, Status newStatus) {
        if (newStatus == null) {
            throw new BusinessRuleException("O status do projeto deve ser informado.");
        }
        if (isFinalStatus(currentStatus) && !currentStatus.equals(newStatus)) {
            throw new BusinessRuleException("Não é possível alterar o status de um projeto finalizado.");
        }
        if (Status.CREATED.equals(currentStatus) && Status.COMPLETED.equals(newStatus)) {
            throw new BusinessRuleException("Um projeto criado deve ser iniciado antes de ser concluído.");
        }
    }

    private boolean isFinalStatus(Status status) {
        return Status.COMPLETED.equals(status) || Status.CANCELED.equals(status);
    }
}
