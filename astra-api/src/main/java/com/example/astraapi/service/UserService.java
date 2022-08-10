package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.UserDto;

import java.util.Optional;

public interface UserService {
  IdDto save(UserDto userDto);

  Optional<UserDto> findUserWithRolesByEmail(String email);

  UserDto getCurrentUser();
}
