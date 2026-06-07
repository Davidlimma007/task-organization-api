package com.davidlima.task_organization_api.mapper;

import com.davidlima.task_organization_api.database.model.Comment;
import com.davidlima.task_organization_api.dto.comment.CommentRequestDTO;
import com.davidlima.task_organization_api.dto.comment.CommentResponseDTO;

public interface ICommentMapper {

    default Comment toComment(CommentRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return Comment.builder()
                .comment(dto.comment())
                .author(MapperUtils.personReference(dto.authorId()))
                .build();
    }

    default CommentResponseDTO toCommentResponseDTO(Comment comment) {
        if (comment == null) {
            return null;
        }
        return new CommentResponseDTO(
                comment.getId(),
                comment.getComment(),
                MapperUtils.personId(comment.getAuthor()),
                MapperUtils.personFullName(comment.getAuthor()),
                MapperUtils.taskId(comment.getTask()),
                MapperUtils.projectId(comment.getProject()),
                comment.getDateCreated()
        );
    }
}
