package com.davidlima.task_organization_api.service;

import com.davidlima.task_organization_api.database.model.Comment;
import com.davidlima.task_organization_api.database.model.Person;
import com.davidlima.task_organization_api.database.model.Project;
import com.davidlima.task_organization_api.database.model.Task;
import com.davidlima.task_organization_api.database.repository.ICommentRepository;
import com.davidlima.task_organization_api.database.repository.IProjectRepository;
import com.davidlima.task_organization_api.database.repository.ITaskRepository;
import com.davidlima.task_organization_api.dto.comment.CommentRequestDTO;
import com.davidlima.task_organization_api.dto.comment.CommentResponseDTO;
import com.davidlima.task_organization_api.enums.Status;
import com.davidlima.task_organization_api.exception.BusinessRuleException;
import com.davidlima.task_organization_api.exception.ResourceNotFoundException;
import com.davidlima.task_organization_api.mapper.ICommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final ICommentRepository commentRepository;
    private final IProjectRepository projectRepository;
    private final ITaskRepository taskRepository;
    private final PersonService personService;
    private final ICommentMapper commentMapper;

    public CommentResponseDTO create(CommentRequestDTO dto) {
        Comment comment = buildComment(dto);
        return commentMapper.toCommentResponseDTO(commentRepository.save(comment));
    }

    public CommentResponseDTO createForProject(UUID projectId, CommentRequestDTO dto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado."));
        if (Status.COMPLETED.equals(project.getStatus()) || Status.CANCELED.equals(project.getStatus())) {
            throw new BusinessRuleException("Não é possível comentar em um projeto finalizado.");
        }
        Comment comment = buildComment(dto);
        comment.setProject(project);
        return commentMapper.toCommentResponseDTO(commentRepository.save(comment));
    }

    public CommentResponseDTO createForTask(UUID taskId, CommentRequestDTO dto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada."));
        if (Status.COMPLETED.equals(task.getStatus()) || Status.CANCELED.equals(task.getStatus())) {
            throw new BusinessRuleException("Não é possível comentar em uma tarefa finalizada.");
        }
        Comment comment = buildComment(dto);
        comment.setTask(task);
        return commentMapper.toCommentResponseDTO(commentRepository.save(comment));
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDTO> findAll() {
        return commentRepository.findAll().stream()
                .map(commentMapper::toCommentResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDTO> findByAuthor(UUID authorId) {
        return commentRepository.findByAuthorId(authorId).stream()
                .map(commentMapper::toCommentResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDTO> findByProject(UUID projectId) {
        return commentRepository.findByProjectId(projectId).stream()
                .map(commentMapper::toCommentResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDTO> findByTask(UUID taskId) {
        return commentRepository.findByTaskId(taskId).stream()
                .map(commentMapper::toCommentResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public CommentResponseDTO findById(UUID id) {
        return commentMapper.toCommentResponseDTO(findEntityById(id));
    }

    public CommentResponseDTO update(UUID id, CommentRequestDTO dto) {
        Comment comment = findEntityById(id);
        Person author = personService.findEntityById(dto.authorId());
        comment.setComment(dto.comment());
        comment.setAuthor(author);
        return commentMapper.toCommentResponseDTO(commentRepository.save(comment));
    }

    public void delete(UUID id) {
        Comment comment = findEntityById(id);
        commentRepository.delete(comment);
    }

    public Comment findEntityById(UUID id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comentário não encontrado."));
    }

    private Comment buildComment(CommentRequestDTO dto) {
        Person author = personService.findEntityById(dto.authorId());
        Comment comment = commentMapper.toComment(dto);
        comment.setAuthor(author);
        return comment;
    }
}
