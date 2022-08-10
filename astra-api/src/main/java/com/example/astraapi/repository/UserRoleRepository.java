package com.example.astraapi.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@Mapper
public interface UserRoleRepository {
  void save(
      @Param("userId") Long userId,
      @Param("roles") Set<String> roles);
}
