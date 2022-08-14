package com.example.astraapi.service.impl;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.UpdateUserDto;
import com.example.astraapi.dto.UserDto;
import com.example.astraapi.entity.UserEntity;
import com.example.astraapi.mapper.UserMapper;
import com.example.astraapi.repository.UserRepository;
import com.example.astraapi.service.AuthContext;
import com.example.astraapi.service.UserRoleService;
import com.example.astraapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final AuthContext authContext;
  private final UserRoleService userRoleService;

  @Override
  @Transactional
  public IdDto save(UserDto userDto) {
    UserEntity userEntity = userMapper.toEntity(userDto);
    userRepository.save(userEntity);
    Long id = userEntity.getId();
    userRoleService.save(id, userDto.getRoles());
    return new IdDto(id);
  }

  @Override
  public Optional<UserDto> findUserWithRolesByEmail(String email) {
    return userRepository.findUserWithRolesByEmail(email)
        .map(userMapper::toDto);
  }

  @Override
  public UserDto getCurrentUser() {
    return authContext.getUser();
  }

  @Override
  public void update(UpdateUserDto user) {
    String email = authContext.getUser().getEmail();
    UserEntity userEntity = userMapper.toEntity(user, email);
    userRepository.update(userEntity);
  }
}
