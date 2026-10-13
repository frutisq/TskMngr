package com.example.tskmngr.controllers;

import com.example.tskmngr.dto.TaskDTO;
import com.example.tskmngr.dto.UpdateTaskStatusRequest;
import com.example.tskmngr.entity.Task;
import com.example.tskmngr.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    public TaskDTO createTask(@RequestBody TaskDTO dto) {
        return taskService.saveTask(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.findAll();
    }


    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    // При создании задачи
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/user/{userId}")
    public Task createUserToTask(@PathVariable Long userId, @RequestBody Task task) {
        return taskService.createTaskForUser(userId, task);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{taskId}/assign/{userId}")
    public TaskDTO addUserToTask(@PathVariable Long userId,
                                 @PathVariable Long taskId) {
        return taskService.assignUserToTask(userId, taskId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Long id,
                              @RequestBody TaskDTO dto) {
        return taskService.updateTask(id, dto);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PatchMapping("/{id}/status")
    public TaskDTO updateStatusTask(@PathVariable Long id,
                                    @RequestBody UpdateTaskStatusRequest request) {
        return taskService.updateTaskStatusRequest(id,request);
    }

}
