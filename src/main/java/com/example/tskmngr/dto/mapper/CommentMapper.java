package com.example.tskmngr.dto.mapper;

import com.example.tskmngr.dto.CommentDTO;
import com.example.tskmngr.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentDTO toDTO(Comment comment) {

        if (comment == null) {
            return null;
        }

        CommentDTO dto = new CommentDTO();

        dto.setId(comment.getId());
        dto.setText(comment.getText());

        if (comment.getUser() != null) {
            dto.setUserId(comment.getUser().getId());
            dto.setUserName(comment.getUser().getUsername());
        }

        if (comment.getTask() != null) {
            dto.setTaskId(comment.getTask().getId());
        }

        return dto;
    }

    public Comment toEntity(CommentDTO dto) {

        if (dto == null) {
            return null;
        }

        Comment comment = new Comment();

        comment.setId(dto.getId());
        comment.setText(dto.getText());

        return comment;
    }
}