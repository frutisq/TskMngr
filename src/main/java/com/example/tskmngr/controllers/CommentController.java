package com.example.tskmngr.controllers;

import com.example.tskmngr.dto.CommentDTO;
import com.example.tskmngr.entity.Comment;

import com.example.tskmngr.repository.CommentRepository;
import com.example.tskmngr.repository.TaskRepository;
import com.example.tskmngr.repository.UserRepository;
import com.example.tskmngr.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/task/{taskId}/user/{userId}")
    public CommentDTO addComment(@PathVariable Long taskId,
                                 @PathVariable Long userId,
                                 @RequestBody CommentDTO dto) {

        return commentService.addComment(taskId, userId, dto);
    }

    @GetMapping("/task/{taskId}")
    public List<CommentDTO> getComments(@PathVariable Long taskId) {

        return commentService.getCommentsByTaskId(taskId);
    }

    @PutMapping("/{id}")
    public CommentDTO updateComment(@PathVariable Long id,
                                    @RequestBody CommentDTO dto) {

        return commentService.updateComment(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}
