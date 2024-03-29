package com.example.astraapi.repository;

import com.example.astraapi.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserRepository {
  void save(@Param("entity") UserEntity entity);

  Optional<UserEntity> findUserWithRolesByEmail(@Param("email") String email);

  void update(@Param("entity") UserEntity entity);
}
