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
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto loginDto) {
        TokenDto tokenDto = authService.login(loginDto);
        return ResponseEntity.ok(tokenDto);
    }

    @PutMapping
    public ResponseEntity<TokenDto> refreshToken(@Valid @RequestBody RefreshTokenDto refreshTokenDto) {
        TokenDto tokenDto = authService.refreshToken(refreshTokenDto);
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<IdDto> signUp(@Valid @RequestBody SignUpDto signUpDto) {
        IdDto idDto = authService.signUp(signUpDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(idDto);
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto) {
        authService.changePassword(changePasswordDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password/reset")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody EmailDto emailDto) {
        authService.resetPassword(emailDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/oauth2/{connection}")
    public ResponseEntity<UrlDto> getLoginUrl(@PathVariable("connection") OAuth2Connection connection) {
        UrlDto urlDto = authService.getLoginUrl(connection);
        return ResponseEntity.ok(urlDto);
    }

    @PostMapping("/oauth2/{connection}")
    public ResponseEntity<TokenDto> oauth2Login(@PathVariable("connection") OAuth2Connection connection, @RequestBody OAuth2CodeDto codeDto) {
        TokenDto tokenDto = authService.login(connection, codeDto);
        return ResponseEntity.ok(tokenDto);
    }
}
