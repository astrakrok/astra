package com.example.astraapi.service;

import com.example.astraapi.dto.UserDto;

import java.util.Optional;

public interface UserService {
  Optional<UserDto> findUserWithRolesByEmail(String email);

  UserDto getCurrentUser();
}
