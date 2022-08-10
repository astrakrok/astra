package com.example.astraapi.service.impl;

import com.example.astraapi.repository.UserRoleRepository;
import com.example.astraapi.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {
  private final UserRoleRepository userRoleRepository;

  @Override
  public void save(Long userId, Set<String> roles) {
    userRoleRepository.save(userId, roles);
  }
}
