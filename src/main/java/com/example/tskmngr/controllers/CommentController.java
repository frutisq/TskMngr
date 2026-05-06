package com.example.tskmngr.controllers;

import com.example.tskmngr.entity.Comment;

import com.example.tskmngr.repository.CommentRepository;
import com.example.tskmngr.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;

    @PostMapping("/api/task/{taskId}/comments")
    public Comment createComment(@PathVariable Long taskId, @RequestBody Comment comment){
     return taskRepository.findById(taskId).map(task -> {
         comment.setTask(task);
         return commentRepository.save(comment);
     }).orElseThrow(()-> new RuntimeException("Task not found"));
    }

    @PutMapping("/api/comments/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody Comment commentDetails){
       return commentRepository.findById(id)
               .map(comment -> {
                   comment.setText(commentDetails.getText());
                   return commentRepository.save(comment);
               }).orElseThrow(()-> new RuntimeException("Comment not found"));
    }

}
