package com.example.astraapi.mapper;

import com.example.astraapi.dto.SignUpDto;
import com.example.astraapi.dto.UserDto;
import com.example.astraapi.mapper.qualifier.TitleQualifier;
import com.example.astraapi.model.GoogleIdTokenPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TitleQualifier.class)
public interface AuthMapper {
  @Mapping(target = "name", qualifiedByName = TitleQualifier.TRIM)
  @Mapping(target = "surname", qualifiedByName = TitleQualifier.TRIM)
  @Mapping(target = "course", qualifiedByName = TitleQualifier.TRIM)
  @Mapping(target = "school", qualifiedByName = TitleQualifier.TRIM)
  UserDto toUserDto(SignUpDto signUpDto);

  @Mapping(target = "name", source = "givenName")
  @Mapping(target = "surname", source = "familyName")
  UserDto toUserDto(GoogleIdTokenPayload payload);

  SignUpDto toSignUpDto(UserDto userDto);
}
