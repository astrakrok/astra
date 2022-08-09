package com.example.astraapi.service;

import com.example.astraapi.dto.LoginDto;
import com.example.astraapi.dto.TokenDto;

public interface AuthService {
  TokenDto login(LoginDto loginDto);
}
