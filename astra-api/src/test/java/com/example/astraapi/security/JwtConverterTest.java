package com.example.astraapi.security;

import com.example.astraapi.dto.UserDto;
import com.example.astraapi.meta.Role;
import com.example.astraapi.model.UserPrincipal;
import com.example.astraapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtConverterTest {
    @InjectMocks
    private JwtConverter jwtConverter;
    @Mock
    private UserService userService;

    @Test
    void shouldReturnUserPrincipal() {
        Jwt jwt = Mockito.mock(Jwt.class);
        Mockito.when(jwt.getClaim("https://astrakrok.com/email")).thenReturn("test@gmail.com");

        Mockito.when(userService.findUserWithRolesByEmail("test@gmail.com")).thenReturn(Optional.of(mockUserDto()));

        AbstractAuthenticationToken token = jwtConverter.convert(jwt);

        assertTrue(token instanceof UserPrincipal);
        UserDto userDto = (UserDto) token.getPrincipal();
        assertEquals("test@gmail.com", userDto.getEmail());
        assertEquals(1, userDto.getRoles().size());
        assertTrue(userDto.getRoles().contains(Role.USER.name()));
    }

    @Test
    void shouldThrowAccessDeniedExceptionWhenUserWasNotFound() {
        Jwt jwt = Mockito.mock(Jwt.class);
        Mockito.when(jwt.getClaim("https://astrakrok.com/email")).thenReturn("test@gmail.com");

        Mockito.when(userService.findUserWithRolesByEmail("test@gmail.com")).thenReturn(Optional.empty());

        AccessDeniedException exception = assertThrows(AccessDeniedException.class, () -> jwtConverter.convert(jwt));

        assertEquals("User not found", exception.getMessage());
    }

    private UserDto mockUserDto() {
        return new UserDto(
                1L,
                "Test",
                "Testovich",
                "test@gmail.com",
                null,
                null,
                null,
                Set.of(Role.USER.name())
        );
    }
}
