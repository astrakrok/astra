package com.example.astraapi.controller;

import com.example.astraapi.dto.auth.ChangePasswordDto;
import com.example.astraapi.dto.EmailDto;
import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.auth.LoginDto;
import com.example.astraapi.dto.auth.OAuth2CodeDto;
import com.example.astraapi.dto.auth.RefreshTokenDto;
import com.example.astraapi.dto.auth.SignUpDto;
import com.example.astraapi.dto.auth.TokenDto;
import com.example.astraapi.dto.UrlDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.meta.OAuth2Connection;
import com.example.astraapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
