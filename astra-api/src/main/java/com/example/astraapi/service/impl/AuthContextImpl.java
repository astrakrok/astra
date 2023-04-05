package com.example.astraapi.service.impl;

import com.example.astraapi.dto.UserDto;
import com.example.astraapi.service.AuthContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthContextImpl implements AuthContext {
    @Override
    public UserDto getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (UserDto) principal;
    }
}
