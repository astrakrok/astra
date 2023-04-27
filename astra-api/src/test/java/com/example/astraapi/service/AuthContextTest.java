package com.example.astraapi.service;

import com.example.astraapi.dto.UserDto;
import com.example.astraapi.service.impl.AuthContextImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthContextTest {
    private AuthContextImpl authContext;

    @BeforeEach
    public void mockSecurityContext() {
        UserDto user = new UserDto();
        user.setEmail("test@gmail.com");
        user.setName("Test");
        user.setSurname("Testovich");
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        authContext = new AuthContextImpl();
    }

    @Test
    void shouldExtractUserFromSecurityContext() {
        UserDto user = authContext.getUser();
        Assertions.assertEquals("test@gmail.com", user.getEmail());
        Assertions.assertEquals("Test", user.getName());
        Assertions.assertEquals("Testovich", user.getSurname());
    }
}
