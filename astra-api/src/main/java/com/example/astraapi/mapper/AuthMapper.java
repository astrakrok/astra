package com.example.astraapi.mapper;

import com.example.astraapi.dto.auth.SignUpDto;
import com.example.astraapi.dto.UserDto;
import com.example.astraapi.mapper.qualifier.TitleQualifier;
import com.example.astraapi.model.OAuth2UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TitleQualifier.class)
public interface AuthMapper {
  @Mapping(target = "name", qualifiedByName = TitleQualifier.TRIM)
  @Mapping(target = "surname", qualifiedByName = TitleQualifier.TRIM)
  @Mapping(target = "school", qualifiedByName = TitleQualifier.TRIM)
  UserDto toUserDto(SignUpDto signUpDto);

  @Mapping(target = "name", source = "givenName")
  @Mapping(target = "surname", source = "familyName")
  UserDto toUserDto(OAuth2UserInfo payload);
}
