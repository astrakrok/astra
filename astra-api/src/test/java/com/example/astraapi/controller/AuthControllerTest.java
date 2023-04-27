package com.example.astraapi.controller;

import com.example.astraapi.dto.EmailDto;
import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.UrlDto;
import com.example.astraapi.dto.auth.*;
import com.example.astraapi.meta.OAuth2Connection;
import com.example.astraapi.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    @InjectMocks
    private AuthController authController;
    @Mock
    private AuthService authService;

    @Test
    void shouldReturnCorrectResponseWhenLogin() {
        when(authService.login(any())).thenReturn(new TokenDto("accessToken", "refreshToken"));

        ResponseEntity<TokenDto> response = authController.login(new LoginDto("email", "password"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("accessToken", response.getBody().getAccessToken());
        assertEquals("refreshToken", response.getBody().getRefreshToken());
    }

    @Test
    void shouldReturnCorrectResponseWhenRefreshToken() {
        when(authService.refreshToken(any())).thenReturn(new TokenDto("accessToken", "refreshToken"));

        ResponseEntity<TokenDto> response = authController.refreshToken(new RefreshTokenDto("refreshToken"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("accessToken", response.getBody().getAccessToken());
        assertEquals("refreshToken", response.getBody().getRefreshToken());
    }

    @Test
    void shouldReturnCorrectResponseWhenSignUp() {
        when(authService.signUp(any())).thenReturn(new IdDto(5L));

        ResponseEntity<IdDto> response = authController.signUp(new SignUpDto("Mock", "Mockovich", 6, "School", "email", "password", null));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(5L, response.getBody().getId());
    }

    @Test
    void shouldReturnCorrectResponseWhenChangePassword() {
        ResponseEntity<Void> response = authController.changePassword(new ChangePasswordDto("oldPassword", "newPassword"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void shouldReturnCorrectResponseWhenResetPassword() {
        ResponseEntity<Void> response = authController.resetPassword(new EmailDto("email"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void shouldReturnCorrectResponseWhenLoginUrl() {
        when(authService.getLoginUrl(any())).thenReturn(new UrlDto("google-url"));

        ResponseEntity<UrlDto> response = authController.getLoginUrl(OAuth2Connection.GOOGLE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("google-url", response.getBody().getUrl());
    }

    @Test
    void shouldReturnCorrectResponseWhenOAuth2Login() {
        when(authService.login(any(), any())).thenReturn(new TokenDto("accessToken", "refreshToken"));

        ResponseEntity<TokenDto> response = authController.oauth2Login(OAuth2Connection.FACEBOOK, new OAuth2CodeDto("code", "redirectUri"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("accessToken", response.getBody().getAccessToken());
        assertEquals("refreshToken", response.getBody().getRefreshToken());
    }
}
