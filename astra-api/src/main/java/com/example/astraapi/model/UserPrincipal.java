package com.example.astraapi.model;

import com.example.astraapi.dto.UserDto;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserPrincipal extends AbstractAuthenticationToken {
    private final UserDto userDto;

    public UserPrincipal(UserDto userDto, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.userDto = userDto;
    }

    @Override
    public boolean isAuthenticated() {
        return userDto != null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userDto;
    }
}
