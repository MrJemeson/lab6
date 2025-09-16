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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceNoMock {
    @Mock
    private UserRepository userRepository;

    private UserServiceImpl userService;
    private List<User> users;

    @BeforeEach
    void setUp(){
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void testAddUserStudent_ValidData() throws CsvValidationException, IOException {
        users = new ArrayList<>();
        when(userRepository.loadUsers()).thenReturn(users);
        userService.addUser("Test1 test1", "Student");

        verify(userRepository).saveUsers(any(List.class));

        List<User> allUsers = userService.getUsers();
        assertEquals(1, allUsers.size());

        User newStudent = allUsers.stream().filter(x -> x.getFULL_NAME().equals("Test1 test1")).toList().get(0);

        assertNotNull(newStudent);
        assertEquals("Student", newStudent.getROLE());
        assertEquals(1, newStudent.getID());
    }

    @Test
    void testAddStudent_NullFullName() {
        assertThrows(IllegalArgumentException.class, () -> userService.addUser(null, "Student"));
    }

    @Test
    void testAddStudent_EmptyFullName() {
        assertThrows(IllegalArgumentException.class, () -> userService.addUser("", "Student"));
    }

    @Test
    void testAddStudent_WhitespaceFullName() {
        assertThrows(IllegalArgumentException.class, () -> userService.addUser("   ", "Student"));
    }

    @Test
    void testAddStudent_NullRole() {
        assertThrows(IllegalArgumentException.class, () -> userService.addUser("Test3 test3", null));
    }

    @Test
    void testAddStudent_EmptyRole() {
        assertThrows(IllegalArgumentException.class, () -> userService.addUser("Test3 test3", ""));
    }
}
