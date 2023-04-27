package com.example.astraapi.controller;

import com.example.astraapi.dto.UpdateUserDto;
import com.example.astraapi.dto.UserDto;
import com.example.astraapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;

    @Test
    void shouldReturnCurrentResponseWhenGetCurrentUser() {
        when(userService.getCurrentUser()).thenReturn(new UserDto(1L, "name", "surname", "email", 3, "school", 3L, Set.of("USER")));

        ResponseEntity<UserDto> response = userController.getCurrentUser();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("name", response.getBody().getName());
        assertEquals("surname", response.getBody().getSurname());
        assertEquals("email", response.getBody().getEmail());
        assertEquals(3, response.getBody().getCourse());
        assertEquals("school", response.getBody().getSchool());
        assertEquals(3L, response.getBody().getSpecializationId());
        assertEquals(1, response.getBody().getRoles().size());
        assertTrue(response.getBody().getRoles().contains("USER"));
    }

    @Test
    void shouldReturnCorrectResponseWhenUpdate() {
        ResponseEntity<Void> response = userController.update(new UpdateUserDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }
}
