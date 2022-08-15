package com.example.astraapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
  private Long id;
  private String name;
  private String surname;
  private String email;
  private String course;
  private String school;
  private Long specializationId;
  private Set<RoleEntity> roles;
}
