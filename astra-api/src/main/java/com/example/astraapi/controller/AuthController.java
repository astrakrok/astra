package com.example.astraapi.controller;

import com.example.astraapi.dto.EmailDto;
import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.UrlDto;
import com.example.astraapi.dto.auth.*;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.meta.OAuth2Connection;
import com.example.astraapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(Endpoint.AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public TokenDto login(@Valid @RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @PutMapping
    public TokenDto refreshToken(@Valid @RequestBody RefreshTokenDto refreshTokenDto) {
        return authService.refreshToken(refreshTokenDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<IdDto> signUp(@Valid @RequestBody SignUpDto signUpDto) {
        IdDto idDto = authService.signUp(signUpDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(idDto);
    }

    @PutMapping("/password")
    public void changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto) {
        authService.changePassword(changePasswordDto);
    }

    @PostMapping("/password/reset")
    public void resetPassword(@Valid @RequestBody EmailDto emailDto) {
        authService.resetPassword(emailDto);
    }

    @GetMapping("/oauth2/{connection}")
    public UrlDto getLoginUrl(@PathVariable("connection") OAuth2Connection connection) {
        return authService.getLoginUrl(connection);
    }

    @PostMapping("/oauth2/{connection}")
    public TokenDto oauth2Login(@PathVariable("connection") OAuth2Connection connection, @RequestBody OAuth2CodeDto codeDto) {
        return authService.login(connection, codeDto);
    }
}
