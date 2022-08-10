package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.LoginDto;
import com.example.astraapi.dto.RefreshTokenDto;
import com.example.astraapi.dto.SignUpDto;
import com.example.astraapi.dto.TokenDto;

public interface AuthService {
  TokenDto login(LoginDto loginDto);

  TokenDto refreshToken(RefreshTokenDto refreshTokenDto);

  IdDto signUp(SignUpDto signUpDto);
}
