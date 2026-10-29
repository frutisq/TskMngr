package com.example.tskmngr.service;

import com.example.tskmngr.dto.TaskDTO;
import com.example.tskmngr.dto.UpdateTaskStatusRequest;
import com.example.tskmngr.dto.mapper.TaskMapper;
import com.example.tskmngr.entity.Task;
import com.example.tskmngr.entity.User;
import com.example.tskmngr.repository.TaskRepository;
import com.example.tskmngr.repository.UserRepository;
import exception.TaskNotFoundException;
import exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public List<TaskDTO> findAll() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO findById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return taskMapper.toDTO(task);
    }

    @Transactional
    public TaskDTO saveTask(TaskDTO dto) {

        Task task = taskMapper.toEntity(dto);

        Task savedTask = taskRepository.save(task);

        return taskMapper.toDTO(savedTask);
    }

    @Transactional
    public TaskDTO updateTask(Long id, TaskDTO dto) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setPriority(dto.getPriority());
        task.setStatus(dto.getStatus());

        Task updatedTask = taskRepository.save(task);

        return taskMapper.toDTO(updatedTask);
    }

    @Transactional
    public TaskDTO updateTaskStatusRequest(Long id, UpdateTaskStatusRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.setStatus(request.getStatus());
        Task updatedTask = taskRepository.save(task);
        return taskMapper.toDTO(updatedTask);
    }

    @Transactional
    public Task createTaskForUser(Long userId, Task task) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        task.setUser(user);
        return taskRepository.save(task);
    }

    @Transactional
    public TaskDTO assignUserToTask(Long userId, Long taskId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        task.setUser(user);
        Task updated = taskRepository.save(task);

        return taskMapper.toDTO(updated);
    }

    @Transactional
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

}
