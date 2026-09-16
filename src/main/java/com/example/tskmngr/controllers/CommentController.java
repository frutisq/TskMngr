package com.example.tskmngr.controllers;

import com.example.tskmngr.dto.CommentDTO;
import com.example.tskmngr.entity.User;
import com.example.tskmngr.repository.UserRepository;
import com.example.tskmngr.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;
    private final UserRepository userRepository;

    @PostMapping("/task/{taskId}")
    public CommentDTO addComment(@PathVariable Long taskId,
                                 @RequestBody CommentDTO dto,
                                 Authentication authentication) {
        String username =  authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return commentService.addComment(taskId,user,dto);
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
