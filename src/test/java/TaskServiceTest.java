//import com.example.tskmngr.entity.Task;
//import com.example.tskmngr.entity.User;
//import com.example.tskmngr.repository.TaskRepository;
//import com.example.tskmngr.repository.UserRepository;
//import com.example.tskmngr.service.TaskService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import org.objectweb.asm.*;
//import static org.hamcrest.Matchers.any;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.client.ExpectedCount.times;
//
//@ExtendWith(MockitoExtension.class)
//public class TaskServiceTest {
//    @Mock
//    private TaskRepository taskRepository;
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private TaskService taskService;
//
//    @Test
//    void assignUserToTask_ShouldAssignUserToTask() {
//        Long userId = 1L;
//        Long taskId = 10L;
//
//        User mockUser = new User();
//        mockUser.setId(userId);
//
//        Task mockTask = new Task();
//        mockTask.setId(taskId);
//
//        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
//        when(taskRepository.findById(taskId)).thenReturn(Optional.of(mockTask));
//        when(taskRepository.save(any(Task.class))).thenAnswer(i -> i.getArguments()[0]);
//
//        Task result = taskService.assignUserToTask(userId, taskId);
//
//        assertNotNull(result.getUser());
//        assertEquals(userId,result.getUser().getId());
//        verify(taskRepository,times(1)).save(any(Task.class));
//    }
//}
