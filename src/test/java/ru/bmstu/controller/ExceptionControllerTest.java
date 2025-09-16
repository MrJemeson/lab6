package ru.bmstu.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.bmstu.dto.ErrorResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ExceptionControllerTest {
    private ExceptionController exceptionController = new ExceptionController();

    @Test
    void testHandleIllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("Invalid input");

        ResponseEntity<ErrorResponse> response = exceptionController.handleIllegalArgumentException(exception);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Unauthorized", response.getBody().getError());
        assertEquals("Invalid input", response.getBody().getMessage());
    }
}
