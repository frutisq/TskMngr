package com.example.tskmngr.controllers;

import com.example.tskmngr.dto.TaskDTO;
import com.example.tskmngr.entity.Task;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
@GetMapping("/{id}")
    public TaskDTO getTask(@PathVariable Long id) {
    TaskDTO dto = new TaskDTO();
    dto.setTitle("Изучить DTO");
    dto.setDescription("Разобраться зачем они нужны");
    dto.setStatus("IN_PROGRESS");
    return dto;
}
}
