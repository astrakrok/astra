package com.example.astraapi.service.impl;

import com.example.astraapi.dto.UserDto;
import com.example.astraapi.meta.ExecutionProfile;
import com.example.astraapi.service.AuthContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile(ExecutionProfile.PRODUCTION)
public class AuthContextImpl implements AuthContext {
    @Override
    public UserDto getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (UserDto) principal;
    }
}
