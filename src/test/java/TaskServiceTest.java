import com.example.tskmngr.dto.TaskDTO;
import com.example.tskmngr.dto.UpdateTaskStatusRequest;
import com.example.tskmngr.dto.mapper.TaskMapper;
import com.example.tskmngr.entity.Priority;
import com.example.tskmngr.entity.Status;
import com.example.tskmngr.entity.Task;
import com.example.tskmngr.entity.User;
import com.example.tskmngr.repository.TaskRepository;
import com.example.tskmngr.repository.UserRepository;
import com.example.tskmngr.service.TaskService;

import exception.TaskNotFoundException;
import exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    @Test
    void saveTask_shouldSaveTaskAndReturnDto() {
        TaskDTO requestDto = new TaskDTO();
        TaskDTO expectedDto = new TaskDTO();

        Task task = new Task();
        Task savedTask = new Task();

        when(taskMapper.toEntity(requestDto))
                .thenReturn(task);

        when(taskRepository.save(task))
                .thenReturn(savedTask);

        when(taskMapper.toDTO(savedTask))
                .thenReturn(expectedDto);

        TaskDTO result = taskService.saveTask(requestDto);

        assertSame(expectedDto, result);

        verify(taskMapper).toEntity(requestDto);
        verify(taskRepository).save(task);
        verify(taskMapper).toDTO(savedTask);
    }

    @Test
    void findTaskById_shouldReturnTask() {
        Task task = new Task();
        TaskDTO expectedDto = new TaskDTO();

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));
        when(taskMapper.toDTO(task))
                .thenReturn(expectedDto);


        TaskDTO result = taskService.findById(1L);

        assertEquals(expectedDto, result);

        verify(taskRepository).findById(1L);
    }

    @Test
    void findAllTasks_shouldReturnTasks() {
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task();
        Task task2 = new Task();
        TaskDTO dto1 = new TaskDTO();
        TaskDTO dto2 = new TaskDTO();

        tasks.add(task1);
        tasks.add(task2);

        when(taskRepository.findAll())
                .thenReturn(tasks);
        when(taskMapper.toDTO(task1))
                .thenReturn(dto1);
        when(taskMapper.toDTO(task2))
                .thenReturn(dto2);

        List<TaskDTO> result = taskService.findAll();

        assertEquals(2, result.size());
    }


    @Test
    void updateTaskStatus_shouldUpdateTask() {

        Task task = new Task();
        task.setId(1L);
        task.setTitle("task1");
        task.setDescription("description");
        task.setPriority(Priority.MEDIUM);
        task.setStatus(Status.TO_DO);


        UpdateTaskStatusRequest request = new UpdateTaskStatusRequest();
        request.setStatus(Status.IN_PROGRESS);

        TaskDTO expectedDto = new TaskDTO();
        expectedDto.setStatus(Status.IN_PROGRESS);

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        when(taskRepository.save(task))
                .thenReturn(task);

        when(taskMapper.toDTO(task))
                .thenReturn(expectedDto);


        TaskDTO updatedTask =
                taskService.updateTaskStatusRequest(1L, request);


        assertEquals(Status.IN_PROGRESS, updatedTask.getStatus());

        verify(taskRepository).findById(1L);
        verify(taskRepository).save(task);
    }

    @Test
    void updateTask_shouldUpdateTaskAndReturnDto() {
        Task task = new Task();


        task.setId(1L);
        task.setTitle("task1");
        task.setDescription("description");
        task.setPriority(Priority.MEDIUM);
        task.setStatus(Status.IN_PROGRESS);

        TaskDTO requestDto = new TaskDTO();
        requestDto.setTitle("new title");
        requestDto.setDescription("new description");
        requestDto.setPriority(Priority.HIGH);
        requestDto.setStatus(Status.IN_PROGRESS);

        TaskDTO expectedDto = new TaskDTO();

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));
        when(taskRepository.save(task))
                .thenReturn(task);
        when(taskMapper.toDTO(task))
                .thenReturn(expectedDto);

        TaskDTO result = taskService.updateTask(1L, requestDto);


        assertSame(expectedDto, result);

        assertEquals("new title", task.getTitle());
        assertEquals("new description", task.getDescription());
        assertEquals(Priority.HIGH, task.getPriority());
        assertEquals(Status.IN_PROGRESS, task.getStatus());
    }

    @Test
    void assignUserToTask_shouldAssignUserToTask() {
        User user = new User();
        user.setId(1L);
        Task task = new Task();
        task.setId(1L);
        task.setTitle("task1");
        task.setDescription("description");
        task.setPriority(Priority.MEDIUM);
        task.setStatus(Status.IN_PROGRESS);

        TaskDTO expectedDto = new TaskDTO();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));
        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));
        when(taskRepository.save(task))
                .thenReturn(task);
        when(taskMapper.toDTO(task))
                .thenReturn(expectedDto);

        TaskDTO result = taskService.assignUserToTask(1L, 1L);

        assertSame(expectedDto, result);

        assertEquals(user, task.getUser());

        verify(userRepository).findById(1L);
        verify(taskRepository).findById(1L);
        verify(taskMapper).toDTO(task);
        verify(taskRepository).save(task);
    }

    @Test
    void deleteTask_shouldDeleteTask() {
        taskService.deleteTask(1L);
        verify(taskRepository).deleteById(1L);

    }

    @Test
    void findById_shouldThrowTaskNotFoundException_whenTaskNotFound() {
        when(taskRepository.findById(1L))
                .thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class,
                () -> taskService.findById(1L));

        assertEquals("Задача с id=1 не найдена", exception.getMessage());

        verify(taskRepository).findById(1L);
        verify(taskMapper, never()).toDTO(any());
    }

    @Test
    void updateTask_shouldThrowTaskNotFoundException_whenTaskNotFound() {
        TaskDTO requestDto = new TaskDTO();
        requestDto.setTitle("new title");

        when(taskRepository.findById(1L))
                .thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class,
                () -> taskService.updateTask(1L, requestDto));

        assertEquals("Задача с id=1 не найдена", exception.getMessage());

        verify(taskRepository).findById(1L);
        verify(taskRepository, never()).save(any());
        verify(taskMapper, never()).toDTO(any());
    }

    @Test
    void updateTaskStatusRequest_shouldThrowTaskNotFoundException_whenTaskNotFound() {
        UpdateTaskStatusRequest request = new UpdateTaskStatusRequest();
        request.setStatus(Status.IN_PROGRESS);

        when(taskRepository.findById(1L))
                .thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class,
                () -> taskService.updateTaskStatusRequest(1L, request));

        assertEquals("Задача с id=1 не найдена", exception.getMessage());

        verify(taskRepository).findById(1L);
        verify(taskRepository, never()).save(any());
    }

    @Test
    void assignUserToTask_shouldThrowUserNotFoundException_whenUserNotFound() {
        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> taskService.assignUserToTask(1L, 1L));

        assertEquals("Пользователь с id=1 не найден", exception.getMessage());

        verify(userRepository).findById(1L);
        verify(taskRepository, never()).findById(any());
        verify(taskRepository, never()).save(any());
    }

    @Test
    void assignUserToTask_shouldThrowTaskNotFoundException_whenTaskNotFound() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));
        when(taskRepository.findById(1L))
                .thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class,
                () -> taskService.assignUserToTask(1L, 1L));

        assertEquals("Задача с id=1 не найдена", exception.getMessage());

        verify(userRepository).findById(1L);
        verify(taskRepository).findById(1L);
        verify(taskRepository, never()).save(any());
        verify(taskMapper, never()).toDTO(any());
    }

    @Test
    void createTaskForUser_shouldThrowUserNotFoundException_whenUserNotFound() {
        Task task = new Task();

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> taskService.createTaskForUser(1L, task));

        assertEquals("Пользователь с id=1 не найден", exception.getMessage());

        verify(userRepository).findById(1L);
        verify(taskRepository, never()).save(any());
    }

}

