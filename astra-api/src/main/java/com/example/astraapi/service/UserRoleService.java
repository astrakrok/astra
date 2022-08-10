package com.example.astraapi.service;

import java.util.Set;

public interface UserRoleService {
  void save(Long userId, Set<String> roles);
}
