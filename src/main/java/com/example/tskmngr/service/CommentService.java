package com.example.tskmngr.service;

import com.example.tskmngr.dto.CommentDTO;
import com.example.tskmngr.dto.mapper.CommentMapper;
import com.example.tskmngr.entity.Comment;
import com.example.tskmngr.entity.Task;
import com.example.tskmngr.entity.User;
import com.example.tskmngr.repository.CommentRepository;
import com.example.tskmngr.repository.TaskRepository;
import com.example.tskmngr.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final CommentMapper commentMapper;

    @Transactional
    public CommentDTO addComment(Long taskId, User user, CommentDTO dto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        Comment comment = commentMapper.toEntity(dto);

        comment.setTask(task);
        comment.setUser(user);

        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toDTO(savedComment);
    }

    public List<CommentDTO> getCommentsByTaskId(Long taskId) {
        return commentRepository.findByTaskId(taskId)
                .stream()
                .map(commentMapper::toDTO)
                .toList();
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    @Transactional
    public CommentDTO updateComment(Long id, CommentDTO dto) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.setText(dto.getText());

        Comment updatedComment = commentRepository.save(comment);

        return commentMapper.toDTO(updatedComment);
    }
}


