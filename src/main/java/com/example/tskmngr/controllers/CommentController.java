package com.example.tskmngr.controllers;

import com.example.tskmngr.entity.Comment;
import com.example.tskmngr.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;

    @PostMapping("/api/task/{taskId}/comments")
    public Comment createComment(@RequestBody Comment comment){
        return commentRepository.save(comment);
    }

    @PutMapping("/api/comments/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody Comment commentDetails){
        Comment comment = commentRepository.findById(id).orElse(null);
        if(comment != null){
            comment.setText(commentDetails.getText());
        }
        return commentRepository.save(commentDetails);
    }

}
