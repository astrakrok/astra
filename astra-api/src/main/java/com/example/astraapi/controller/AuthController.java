package com.example.astraapi.controller;

import com.example.astraapi.dto.LoginDto;
import com.example.astraapi.dto.TokenDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
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
  public TokenDto login(@RequestBody @Valid LoginDto loginDto) {
    return authService.login(loginDto);
  }
}
