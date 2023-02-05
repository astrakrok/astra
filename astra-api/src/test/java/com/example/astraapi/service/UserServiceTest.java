package com.example.astraapi.service;

import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.UpdateUserDto;
import com.example.astraapi.dto.UserDto;
import com.example.astraapi.entity.RoleEntity;
import com.example.astraapi.entity.UserEntity;
import com.example.astraapi.mapper.UserMapper;
import com.example.astraapi.mapper.qualifier.RoleQualifier;
import com.example.astraapi.mapper.qualifier.TitleQualifier;
import com.example.astraapi.repository.UserRepository;
import com.example.astraapi.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private AuthContext authContext;
    @Mock
    private UserRoleService userRoleService;

    @BeforeEach
    public void beforeEach() {
        UserMapper mapper = Mappers.getMapper(UserMapper.class);
        ReflectionTestUtils.setField(mapper, "titleQualifier", new TitleQualifier());
        ReflectionTestUtils.setField(mapper, "roleQualifier", new RoleQualifier());

        lenient().when(userMapper.toEntity(any())).thenAnswer(invocation -> mapper.toEntity(invocation.getArgument(0)));
        lenient().when(userMapper.toEntity(any(), any())).thenAnswer(invocation -> mapper.toEntity(
                invocation.getArgument(0),
                invocation.getArgument(1)));
        lenient().when(userMapper.toDto(any())).thenAnswer(invocation -> mapper.toDto(invocation.getArgument(0)));
    }

    @Test
    void shouldSaveUser() {
        UserDto userDto = new UserDto();
        userDto.setCourse(3);
        userDto.setName("name");
        userDto.setSchool("school");
        userDto.setEmail("example@test.email");
        userDto.setSurname("surname");
        userDto.setSpecializationId(4L);

        doAnswer(invocation -> {
            UserEntity userEntity = invocation.getArgument(0);
            userEntity.setId(10L);
            return userEntity;
        }).when(userRepository).save(any());

        IdDto idDto = userService.save(userDto);

        assertEquals(10L, idDto.getId());
    }

    @Test
    void shouldFindUserWithRolesByEmail() {
        when(userRepository.findUserWithRolesByEmail(any())).thenReturn(Optional.of(new UserEntity(
                10L,
                "name",
                "surname",
                "test@email.com",
                null,
                null,
                null,
                Set.of(
                        new RoleEntity(
                                1L,
                                "USER"
                        ),
                        new RoleEntity(
                                2L,
                                "ADMIN"
                        )
                )
        )));

        Optional<UserDto> user = userService.findUserWithRolesByEmail("test@email.com");

        assertFalse(user.isEmpty());
        assertEquals(2, user.get().getRoles().size());
    }

    @Test
    void shouldReturnCurrentUser() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);

        when(authContext.getUser()).thenReturn(userDto);

        UserDto currentUser = userService.getCurrentUser();

        assertEquals(1L, currentUser.getId());
    }

    @Test
    void shouldUpdateCurrentUser() {
        String[] email = new String[1];

        UserDto userDto = new UserDto();
        userDto.setEmail("test@email.com");
        when(authContext.getUser()).thenReturn(userDto);

        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setName("name");
        updateUserDto.setSurname("surname");

        doAnswer(invocation -> {
            UserEntity entity = invocation.getArgument(0);
            email[0] = entity.getEmail();

            return null;
        }).when(userRepository).update(any());

        userService.update(updateUserDto);

        assertEquals("test@email.com", email[0]);
    }
}
