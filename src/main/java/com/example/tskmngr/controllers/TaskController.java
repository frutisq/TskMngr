package com.example.tskmngr.controllers;

import com.example.tskmngr.dto.TaskDTO;
import com.example.tskmngr.dto.mapper.TaskMapper;
import com.example.tskmngr.entity.Task;
import com.example.tskmngr.repository.TaskRepository;
import com.example.tskmngr.repository.UserRepository;
import com.example.tskmngr.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskDTO createTask(@RequestBody TaskDTO dto) {
        return taskService.saveTask(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.findAll();
    }


    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @PostMapping("/user/{userId}")
    public Task createUserToTask(@PathVariable Long userId, @RequestBody Task task) {
        return taskService.createTaskForUser(userId, task);
    }

    @PatchMapping("/{taskId}/assign/{userId}")
    public TaskDTO addUserToTask(@PathVariable Long userId,
                                 @PathVariable Long taskId) {
        return taskService.assignUserToTask(userId, taskId);
    }

    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Long id,
                              @RequestBody TaskDTO dto) {
        return taskService.updateTask(id, dto);
    }

}
