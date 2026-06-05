package com.davidlima.task_organization_api.mapper;

import com.davidlima.task_organization_api.database.model.Comment;
import com.davidlima.task_organization_api.dto.comment.CommentRequestDTO;
import com.davidlima.task_organization_api.dto.comment.CommentResponseDTO;

public interface ICommentMapper {

    Comment toComment(CommentRequestDTO dto);

    CommentResponseDTO toCommentResponseDTO(Comment comment);
}
