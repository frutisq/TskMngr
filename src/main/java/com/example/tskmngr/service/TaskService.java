package com.example.tskmngr.service;

import com.example.tskmngr.entity.Task;
import com.example.tskmngr.entity.User;
import com.example.tskmngr.repository.TaskRepository;
import com.example.tskmngr.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @Transactional
    public Task updateTask(Long id, Task taskDetails) {
        Task task = findById(id);
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());
        task.setPriority(taskDetails.getPriority());
        return taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Transactional
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Transactional
    public Task createTaskForUser(Long userId, Task task) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        task.setUser(user);
        return taskRepository.save(task);
    }

    @Transactional
    public Task assignUserToTask(Long userId, Long taskId) {
       User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
       Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
       task.setUser(user);
       return taskRepository.save(task);
    }

}
