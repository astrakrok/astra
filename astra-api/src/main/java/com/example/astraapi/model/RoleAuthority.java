package com.example.astraapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@AllArgsConstructor
public class RoleAuthority implements GrantedAuthority {
    private final String role;

    @Override
    public String getAuthority() {
        return role;
    }
}
