package ru.bmstu.controller;

import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.bmstu.dto.*;
import ru.bmstu.object.User;
import ru.bmstu.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class MainControllerTest {

    @Mock
    private UserService userService;

    private MainController mainController;

    @BeforeEach
    void setUp() {
        mainController = new MainController(userService);
    }

    @Test
    void testGetStatus() {
        ResponseEntity<String> response = mainController.getAppStatus();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("App working mormally", response.getBody());
    }

    @Test
    void testGetUserById_Success() throws CsvValidationException, IOException {
        User user = new User(1, "Test1 test", "Student", 10);
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userService.getUsers()).thenReturn(users);

        ResponseEntity<?> response = mainController.getStudentById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testGetUserById_NoSuchElementException() {
//        when(userService.getStudentByID(999))
//                .thenThrow(new NoSuchElementException("User with id 999 not found"));

        ResponseEntity<?> response = mainController.getStudentById(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals("User not found", errorResponse.getError());
        assertEquals("User with id=999 not found", errorResponse.getMessage());
    }

    @Test
    void testCreateStudent_Success(){
        CreateRequest request = new CreateRequest();
        request.setFullName("Test1 test1");
        request.setRole("Student");

//        doNothing().when(userService).addUser("Test1 test1", "Student");

        ResponseEntity<?> response = mainController.createUser("Alexey Pitikin", request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody() instanceof SuccessResponse);
        SuccessResponse successResponse = (SuccessResponse) response.getBody();
        assertEquals("User created successfully", successResponse.getMessage());
    }

    @Test
    void testCreateStudent_IllegalArgumentException(){
        CreateRequest request = new CreateRequest();
        request.setFullName("");
        request.setRole("Student");

        doThrow(new IllegalArgumentException("Name cannot be null or empty"))
                .when(userService).addUser("", "Student");

        ResponseEntity<?> response = mainController.createUser("Alexey Pitikin", request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals("Bad Request", errorResponse.getError());
        assertEquals("Name cannot be null or empty", errorResponse.getMessage());
    }

    @Test
    void testUpdateStudentTokens_Success(){
        UpdateRequest request = new UpdateRequest();
        request.setAmount(200);

//        doNothing().when(userService).updateUser(1, 200);

        ResponseEntity<?> response = mainController.updateUser("Alexey Pitikin", 1, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof SuccessResponse);
        SuccessResponse successResponse = (SuccessResponse) response.getBody();
        assertEquals("Tokens updated successfully", successResponse.getMessage());
    }

    @Test
    void testUpdateStudentTags_NoSuchElementException() {
        UpdateRequest request = new UpdateRequest();
        request.setAmount(200);

        doThrow(new NoSuchElementException("User with id 999 not found"))
                .when(userService).updateUser(999, 200);

        ResponseEntity<?> response = mainController.updateUser("Alexey Pitikin", 999, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals("Not Found", errorResponse.getError());
        assertEquals("User with id 999 not found", errorResponse.getMessage());
    }

    @Test
    void testDeleteStudent_Success(){
//        doNothing().when(userService).deleteUser(1);

        ResponseEntity<?> response = mainController.deleteStudent("Alexey Pitikin", 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof SuccessResponse);
        SuccessResponse successResponse = (SuccessResponse) response.getBody();
        assertEquals("Student deleted successfully", successResponse.getMessage());
    }

    @Test
    void testDeleteStudent_NoSuchElementException() {
        doThrow(new NoSuchElementException("User with id 999 not found"))
                .when(userService).deleteUser(999);

        ResponseEntity<?> response = mainController.deleteStudent("Alexey Pitikin", 999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals("Not Found", errorResponse.getError());
        assertEquals("User with id 999 not found", errorResponse.getMessage());
    }
}
