package com.example.tskmngr.dto.mapper;

import com.example.tskmngr.dto.TaskDTO;
import com.example.tskmngr.entity.Priority;
import com.example.tskmngr.entity.Status;
import com.example.tskmngr.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TaskDTO toDTO(Task task) {
        if (task == null) {
            return null;
        }

        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setStatus(task.getStatus());
        taskDTO.setPriority(task.getPriority());

        if (task.getUser() != null) {
            taskDTO.setUserId(task.getUser().getId());
            taskDTO.setUserName(task.getUser().getUsername());
        }

        return taskDTO;
    }
    public Task toEntity(TaskDTO dto) {

        Task task = new Task();

        task.setId(dto.getId());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());

        return task;
    }
}
