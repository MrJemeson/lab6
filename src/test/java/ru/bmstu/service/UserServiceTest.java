package ru.bmstu.service;

import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.bmstu.object.User;
import ru.bmstu.repository.UserRepository;
import ru.bmstu.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    private UserServiceImpl userService;
    private List<User> users;

    @BeforeEach
    void setUp() {
        users = new ArrayList<>();
        users.add(new User(1, "Test1 test1", "Student", 10));
        users.add(new User(2, "Test2 test2", "Student", 10));

        when(userRepository.loadUsers()).thenReturn(users);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void testGetAllStudents() throws CsvValidationException, IOException {
        List<User> result = userService.getUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test1 test1", result.get(0).getFULL_NAME());
        assertEquals("Test2 test2", result.get(1).getFULL_NAME());
    }

    @Test
    void testDeleteStudent_ExistingStudent() {
        userService.deleteUser(1);

        verify(userRepository).saveUsers(any(List.class));

        List<User> allUsers = userService.getUsers();
        assertEquals(1, allUsers.size());
        assertEquals("Test2 test2", allUsers.get(0).getFULL_NAME());
    }

    @Test
    void testDeleteStudent_NonExistingStudent() {
        assertThrows(NoSuchElementException.class, () -> userService.deleteUser(10));
    }

    @Test
    void testUpdateTokens_ExistingStudent() {
        User updatedStudent = userService.updateUser(1, 100);
        verify(userRepository).saveUsers(any(List.class));
        assertEquals(110, updatedStudent.getTokens());
    }

    @Test
    void testUpdateTokens_NonExistingStudent() {
        assertThrows(NoSuchElementException.class, () -> userService.updateUser(999, 300));
    }

    @Test
    void testUpdateTokens_NegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(1, -20));
    }
}
