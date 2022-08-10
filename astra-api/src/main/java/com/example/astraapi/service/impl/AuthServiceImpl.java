package com.example.astraapi.service.impl;

import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.AuthRequest;
import com.auth0.net.Request;
import com.auth0.net.SignUpRequest;
import com.auth0.net.TokenRequest;
import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.LoginDto;
import com.example.astraapi.dto.RefreshTokenDto;
import com.example.astraapi.dto.SignUpDto;
import com.example.astraapi.dto.TokenDto;
import com.example.astraapi.dto.UserDto;
import com.example.astraapi.exception.AlreadyExistsException;
import com.example.astraapi.exception.AuthProviderException;
import com.example.astraapi.mapper.AuthMapper;
import com.example.astraapi.mapper.TokenMapper;
import com.example.astraapi.meta.Role;
import com.example.astraapi.security.SecurityProperties;
import com.example.astraapi.service.AuthService;
import com.example.astraapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final TokenMapper tokenMapper;
  private final AuthAPI auth;
  private final SecurityProperties securityProperties;
  private final UserService userService;
  private final AuthMapper authMapper;

  @Override
  public TokenDto login(LoginDto loginDto) {
    AuthRequest request = auth.login(loginDto.getEmail(), loginDto.getPassword().toCharArray())
        .setAudience(securityProperties.getAudience())
        .setScope(securityProperties.getScope());
    TokenHolder holder = execute(request);
    return tokenMapper.toDto(holder);
  }

  @Override
  public TokenDto refreshToken(RefreshTokenDto refreshTokenDto) {
    TokenRequest request = auth.renewAuth(refreshTokenDto.getRefreshToken())
        .setScope(securityProperties.getScope());
    TokenHolder holder = execute(request);
    return tokenMapper.toDto(holder);
  }

  @Override
  @Transactional
  public IdDto signUp(SignUpDto signUpDto) {
    userService.findUserWithRolesByEmail(signUpDto.getEmail())
        .ifPresent(this::userExists);
    SignUpRequest request = auth.signUp(
        signUpDto.getEmail(),
        signUpDto.getPassword().toCharArray(),
        securityProperties.getConnection());
    UserDto userDto = authMapper.toUserDto(signUpDto);
    userDto.getRoles().add(Role.USER.name());
    IdDto idDto = userService.save(userDto);
    execute(request);
    return idDto;
  }

  private <T> T execute(Request<T> request) {
    try {
      return request.execute();
    } catch (Auth0Exception exception) {
      throw new AuthProviderException("An unexpected error occurred");
    }
  }

  private void userExists(UserDto userDto) {
    throw new AlreadyExistsException("User with such email already exists");
  }
}
