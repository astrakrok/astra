package com.example.astraapi.security;

import com.example.astraapi.dto.UserDto;
import com.example.astraapi.model.RoleAuthority;
import com.example.astraapi.model.UserPrincipal;
import com.example.astraapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {
  private final UserService userService;

  @Override
  public AbstractAuthenticationToken convert(Jwt source) {
    String email = source.getClaim("https://astrakrok.com/email");
    UserDto user = userService.findUserWithRolesByEmail(email).orElse(null);
    List<RoleAuthority> grantedAuthorities = getGrantedAuthorities(user);
    return new UserPrincipal(user, grantedAuthorities);
  }

  private List<RoleAuthority> getGrantedAuthorities(UserDto userDto) {
    return userDto.getRoles().stream()
        .map(RoleAuthority::new)
        .collect(Collectors.toList());
  }
}
