package com.example.astraapi.mapper;

import com.auth0.json.auth.TokenHolder;
import com.example.astraapi.dto.auth.TokenDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenMapper {
  TokenDto toDto(TokenHolder holder);
}
