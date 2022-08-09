package com.example.astraapi.service.impl;

import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.AuthRequest;
import com.example.astraapi.dto.LoginDto;
import com.example.astraapi.dto.TokenDto;
import com.example.astraapi.exception.AuthProviderException;
import com.example.astraapi.mapper.TokenMapper;
import com.example.astraapi.security.SecurityProperties;
import com.example.astraapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final TokenMapper tokenMapper;
  private final AuthAPI auth;
  private final SecurityProperties properties;

  @Override
  public TokenDto login(LoginDto loginDto) {
    AuthRequest request = auth.login(loginDto.getEmail(), loginDto.getPassword().toCharArray())
        .setAudience(properties.getAudience())
        .setScope(properties.getScope());
    try {
      TokenHolder holder = request.execute();
      return tokenMapper.toDto(holder);
    } catch (Auth0Exception e) {
      throw new AuthProviderException("An unexpected error occured while trying to login use", e);
    }
  }
}
