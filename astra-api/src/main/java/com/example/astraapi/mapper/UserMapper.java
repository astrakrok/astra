package com.example.astraapi.mapper;

import com.example.astraapi.dto.UpdateUserDto;
import com.example.astraapi.dto.UserDto;
import com.example.astraapi.entity.UserEntity;
import com.example.astraapi.mapper.qualifier.RoleQualifier;
import com.example.astraapi.mapper.qualifier.TitleQualifier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
    RoleQualifier.class,
    TitleQualifier.class})
public interface UserMapper {
  @Mapping(target = "roles", source = "roles", qualifiedByName = RoleQualifier.ROLE_ENTITY_TO_STRING)
  UserDto toDto(UserEntity entity);

  @Mapping(target = "name", qualifiedByName = TitleQualifier.TRIM)
  @Mapping(target = "surname", qualifiedByName = TitleQualifier.TRIM)
  @Mapping(target = "school", qualifiedByName = TitleQualifier.TRIM)
  @Mapping(target = "roles", ignore = true)
  UserEntity toEntity(UserDto userDto);

  @Mapping(target = "name", qualifiedByName = TitleQualifier.TRIM)
  @Mapping(target = "surname", qualifiedByName = TitleQualifier.TRIM)
  @Mapping(target = "school", qualifiedByName = TitleQualifier.TRIM)
  UserEntity toEntity(UpdateUserDto userDto, String email);
}
