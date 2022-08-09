package com.example.astraapi.service.impl;

import com.example.astraapi.dto.UserDto;
import com.example.astraapi.mapper.UserMapper;
import com.example.astraapi.repository.UserRepository;
import com.example.astraapi.service.AuthContext;
import com.example.astraapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final AuthContext authContext;

  @Override
  public Optional<UserDto> findUserWithRolesByEmail(String email) {
    return userRepository.findUserWithRolesByEmail(email)
        .map(userMapper::toDto);
  }

  @Override
  public UserDto getCurrentUser() {
    return authContext.getUser();
  }
}
