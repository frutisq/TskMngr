package com.example.tskmngr.service;

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

@Service
@AllArgsConstructor
public class CommentService {
    private CommentRepository commentRepository;
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    @Transactional
    public Comment addComment(Long taskId,Long userId, Comment comment) {
        Task task = taskRepository.findById(taskId).orElseThrow(()-> new RuntimeException("Task not found"));
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        comment.setTask(task);
        comment.setUser(user);
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByTaskId(Long taskId) {
        return commentRepository.findByTaskId(taskId);
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

//    @Transactional
//    public Comment updateComment(Long id, Comment commentDetails) {
//        Comment comment = commentRepository.findById(id).orElseThrow(()-> new RuntimeException("..."));
//        comment.setText(commentDetails.getText());
//        return commentRepository.save(comment);
//    }

}
