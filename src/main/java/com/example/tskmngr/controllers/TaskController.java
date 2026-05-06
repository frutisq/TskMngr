package com.example.tskmngr.controllers;

import com.example.tskmngr.dto.TaskDTO;
import com.example.tskmngr.entity.Task;
import com.example.tskmngr.repository.TaskRepository;
import com.example.tskmngr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    @PostMapping("/user/{userId}")
    public Task addUserToTask(@PathVariable Long userId, @RequestBody Task task) {
        return userRepository.findById(userId).map(user ->
        {task.setUser(user);
            return taskRepository.save(task);
        }).orElseThrow(()-> new RuntimeException("User not found"));
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setPriority(taskDetails.getPriority());
        task.setStatus(taskDetails.getStatus());

        return taskRepository.save(task);

    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }

    @GetMapping("/{id}")
    public TaskDTO getTask(@PathVariable Long id) {
        TaskDTO dto = new TaskDTO();
        dto.setTitle("Изучить DTO");
        dto.setDescription("Разобраться зачем они нужны");
        dto.setStatus("IN_PROGRESS");
        return dto;
    }
}
