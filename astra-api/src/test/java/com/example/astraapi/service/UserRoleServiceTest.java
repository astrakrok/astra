package com.example.astraapi.service;

import com.example.astraapi.meta.Role;
import com.example.astraapi.repository.UserRoleRepository;
import com.example.astraapi.service.impl.UserRoleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class UserRoleServiceTest {
    @InjectMocks
    private UserRoleServiceImpl userRoleService;
    @Mock
    private UserRoleRepository userRoleRepository;

    @Test
    void shouldSaveUserRoles() {
        Long[] passedUserId = new Long[1];
        Set<String> passedRoles = new HashSet<>();

        Mockito.doAnswer(invocation -> {
            Long userId = invocation.getArgument(0);
            Set<String> roles = invocation.getArgument(1);

            passedUserId[0] = userId;
            passedRoles.addAll(roles);

            return null;
        }).when(userRoleRepository).save(ArgumentMatchers.any(), ArgumentMatchers.anySet());

        userRoleService.save(4L, Set.of(Role.USER.name(), Role.ADMIN.name()));

        assertEquals(4L, passedUserId[0]);
        assertEquals(2, passedRoles.size());
        assertTrue(passedRoles.contains(Role.USER.name()));
        assertTrue(passedRoles.contains(Role.ADMIN.name()));
    }
}
