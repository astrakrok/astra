package com.example.astraapi.mock;

import com.example.astraapi.dto.UserDto;
import com.example.astraapi.meta.ExecutionProfile;
import com.example.astraapi.service.AuthContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Profile(ExecutionProfile.INTEGRATION_TEST)
public class AuthContextMock implements AuthContext {
    @Override
    public UserDto getUser() {
        return new UserDto(
                101L,
                "Mock",
                "Mockovich",
                "mock@email.com",
                null,
                null,
                null,
                Set.of("USER", "ADMIN", "SUPER_ADMIN")
        );
    }
}
