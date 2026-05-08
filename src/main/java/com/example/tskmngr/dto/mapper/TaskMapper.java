package com.example.tskmngr.dto.mapper;

import com.example.tskmngr.dto.TaskDTO;
import com.example.tskmngr.entity.Task;

public class TaskMapper {
    public TaskDTO toTaskDTO(Task task) {
        if (task == null) {
            return null;
        }

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setStatus(task.getStatus().name());
        taskDTO.setPriority(task.getPriority().name());

        if (task.getUser() != null) {
            taskDTO.setUserId(task.getUser().getId());
        }

        return taskDTO;
    }
}
