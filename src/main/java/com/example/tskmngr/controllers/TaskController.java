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
    public Task createTask(@RequestBody Task task) {
        return taskService.saveTask(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long taskId, @RequestBody Task taskDetails) {
        return taskService.updateTask(taskId, taskDetails);

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
    public Task getTaskById(@RequestParam("id") Long id) {
        return taskService.findById(id);
    }

    @PostMapping("/user/{userId}")
    public Task createUserToTask(@PathVariable Long userId, @RequestBody Task task) {
        return taskService.createTaskForUser(userId, task);
    }

    @PatchMapping("/{taskId}/assign/{userId}")
    public Task addUserToTask(@PathVariable Long userId,@PathVariable Long taskId) {
        return taskService.assignUserToTask(userId, taskId);
    }

}
