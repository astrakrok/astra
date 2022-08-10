package com.example.astraapi.controller;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.LoginDto;
import com.example.astraapi.dto.RefreshTokenDto;
import com.example.astraapi.dto.SignUpDto;
import com.example.astraapi.dto.TokenDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
