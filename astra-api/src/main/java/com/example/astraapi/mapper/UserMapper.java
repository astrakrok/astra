package com.example.astraapi.mapper;

import com.example.astraapi.dto.UserDto;
import com.example.astraapi.entity.UserEntity;
import com.example.astraapi.mapper.qualifier.RoleQualifier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = RoleQualifier.class)
public interface UserMapper {
  @Mapping(target = "roles", source = "roles", qualifiedByName = RoleQualifier.ROLE_ENTITY_TO_STRING)
  UserDto toDto(UserEntity entity);
}
