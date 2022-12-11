package com.example.astraapi.service;

import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.client.mgmt.UsersEntity;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.json.auth.TokenHolder;
import com.auth0.json.mgmt.users.Identity;
import com.auth0.json.mgmt.users.User;
import com.auth0.net.Request;
import com.auth0.net.SignUpRequest;
import com.auth0.net.TokenRequest;
import com.example.astraapi.dto.EmailDto;
import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.UrlDto;
import com.example.astraapi.dto.UserDto;
import com.example.astraapi.dto.auth.ChangePasswordDto;
import com.example.astraapi.dto.auth.LoginDto;
import com.example.astraapi.dto.auth.OAuth2CodeDto;
import com.example.astraapi.dto.auth.RefreshTokenDto;
import com.example.astraapi.dto.auth.SignUpDto;
import com.example.astraapi.dto.auth.TokenDto;
import com.example.astraapi.exception.AlreadyExistsException;
import com.example.astraapi.exception.AuthProviderException;
import com.example.astraapi.mapper.AuthMapper;
import com.example.astraapi.mapper.TokenMapper;
import com.example.astraapi.meta.OAuth2Connection;
import com.example.astraapi.meta.Role;
import com.example.astraapi.model.OAuth2UserInfo;
import com.example.astraapi.security.SecurityProperties;
import com.example.astraapi.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
  @InjectMocks
  private AuthServiceImpl authService;
  @Spy
  private AuthMapper authMapper = Mappers.getMapper(AuthMapper.class);
  @Spy
  private TokenMapper tokenMapper = Mappers.getMapper(TokenMapper.class);
  @Mock
  private Auth0Executor executor;
  @Mock
  private Auth0ManagementService managementService;
  @Mock
  private AuthAPI auth;
  @Spy
  private SecurityProperties securityProperties;
  @Mock
  private UserService userService;
  @Mock
  private AuthContext authContext;
  @Spy
  private Map<OAuth2Connection, OAuth2Provider> providers;

  @Test
  void shouldReturnTokenOnLogin() {
    TokenHolder tokenHolder = new TokenHolder("accessToken", "idToken", "refreshToken", "Bearer", 1000000, "email, profile", new Date());
    TokenRequest tokenRequest = Mockito.mock(TokenRequest.class);
    Mockito.when(tokenRequest.setScope(ArgumentMatchers.any())).thenReturn(tokenRequest);
    Mockito.when(tokenRequest.setAudience(ArgumentMatchers.any())).thenReturn(tokenRequest);
    Mockito.when(executor.execute(ArgumentMatchers.any())).thenReturn(tokenHolder);
    Mockito.when(auth.login(ArgumentMatchers.any(), ArgumentMatchers.any(char[].class))).thenReturn(tokenRequest);

    TokenDto tokenDto = authService.login(new LoginDto("test@gmail.com", "password"));

    assertEquals("accessToken", tokenDto.getAccessToken());
    assertEquals("refreshToken", tokenDto.getRefreshToken());
  }

  @Test
  void shouldThrowExceptionOnLogin() throws Auth0Exception {
    TokenRequest tokenRequest = Mockito.mock(TokenRequest.class);
    Mockito.when(tokenRequest.setScope(ArgumentMatchers.any())).thenReturn(tokenRequest);
    Mockito.when(tokenRequest.setAudience(ArgumentMatchers.any())).thenReturn(tokenRequest);
    Mockito.when(executor.execute(ArgumentMatchers.any())).thenThrow(new AuthProviderException("An unexpected error occurred"));
    Mockito.when(auth.login(ArgumentMatchers.any(), ArgumentMatchers.any(char[].class))).thenReturn(tokenRequest);

    AuthProviderException exception = assertThrows(AuthProviderException.class, () -> authService.login(new LoginDto("test@gmail.com", "password")));

    assertEquals("An unexpected error occurred", exception.getMessage());
  }

  @Test
  void shouldRenewAuth() {
    TokenHolder tokenHolder = new TokenHolder("accessToken", "idToken", "refreshToken", "Bearer", 1000000, "email, profile", new Date());
    TokenRequest tokenRequest = Mockito.mock(TokenRequest.class);
    Mockito.when(tokenRequest.setScope(ArgumentMatchers.any())).thenReturn(tokenRequest);
    Mockito.when(executor.execute(ArgumentMatchers.any())).thenReturn(tokenHolder);
    Mockito.when(auth.renewAuth(ArgumentMatchers.any())).thenReturn(tokenRequest);

    TokenDto tokenDto = authService.refreshToken(new RefreshTokenDto("validRefreshToken"));

    assertEquals("accessToken", tokenDto.getAccessToken());
    assertEquals("refreshToken", tokenDto.getRefreshToken());
  }

  @Test
  void shouldThrowExceptionWhenSignUpUserWithExistingEmail() {
    Mockito.when(userService.findUserWithRolesByEmail("test@gmail.com")).thenReturn(Optional.of(new UserDto()));
    AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> authService.signUp(new SignUpDto("Test", "Testovich", null, null, "test@gmail.com", "password", null)));
    assertEquals("User with such email already exists", exception.getMessage());
  }

  @Test
  void shouldSignUpUserAndWithUserRoleReturnId() throws Auth0Exception {
    String email = "test@gmail.com";
    CreatedUser auth0User = new CreatedUser();
    ReflectionTestUtils.setField(auth0User, "email", email);
    Mockito.when(authMapper.toUserDto(ArgumentMatchers.any(SignUpDto.class))).thenAnswer(invocation -> {
      SignUpDto dto = invocation.getArgument(0);
      return new UserDto(
          null,
          dto.getName(),
          dto.getSurname(),
          dto.getEmail(),
          dto.getCourse(),
          dto.getSchool(),
          dto.getSpecializationId(),
          new HashSet<>());
    });
    Mockito.when(userService.findUserWithRolesByEmail(email)).thenReturn(Optional.empty());
    SignUpRequest request = Mockito.mock(SignUpRequest.class);
    Mockito.when(auth.signUp(ArgumentMatchers.any(), ArgumentMatchers.any(char[].class), ArgumentMatchers.any())).thenReturn(request);
    Mockito.when(executor.execute(ArgumentMatchers.any(SignUpRequest.class))).thenReturn(auth0User);
    UserDto[] userDto = new UserDto[1];
    Mockito.when(userService.save(ArgumentMatchers.any())).thenAnswer(invocation -> {
      userDto[0] = invocation.getArgument(0);
      return new IdDto(5L);
    });

    IdDto idDto = authService.signUp(new SignUpDto("Test", "Testovich", null, null, "test@gmail.com", "password", null));

    assertEquals(5L, idDto.getId());
    assertEquals(1, userDto[0].getRoles().size());
    assertTrue(userDto[0].getRoles().contains(Role.USER.name()));
    assertEquals(email, userDto[0].getEmail());
  }

  @Test
  void shouldChangePassword() {
    User[] newUser = new User[1];
    Mockito.when(securityProperties.getConnection()).thenReturn("Username-Password-Authentication");

    UserDto userDto = new UserDto();
    userDto.setEmail("test@gmail.com");
    Mockito.when(authContext.getUser()).thenReturn(userDto);

    TokenRequest tokenRequest = Mockito.mock(TokenRequest.class);
    Mockito.when(tokenRequest.setAudience(ArgumentMatchers.any())).thenReturn(tokenRequest);
    Mockito.when(tokenRequest.setScope(ArgumentMatchers.any())).thenReturn(tokenRequest);

    Mockito.when(auth.login(ArgumentMatchers.any(), ArgumentMatchers.any(char[].class))).thenReturn(tokenRequest);

    Request<List<User>> usersListRequest = (Request<List<User>>) Mockito.mock(Request.class);

    UsersEntity usersEntity = Mockito.mock(UsersEntity.class);
    Mockito.when(usersEntity.listByEmail(ArgumentMatchers.same("test@gmail.com"), ArgumentMatchers.any())).thenReturn(usersListRequest);
    Mockito.when(usersEntity.update(ArgumentMatchers.any(), ArgumentMatchers.any())).thenAnswer(invocation -> {
      newUser[0] = invocation.getArgument(1);
      return (Request<User>) Mockito.mock(Request.class);
    });

    ManagementAPI managementApi = Mockito.mock(ManagementAPI.class);
    Mockito.when(managementApi.users()).thenReturn(usersEntity);

    Mockito.when(executor.execute(tokenRequest)).thenReturn(null);
    Mockito.when(executor.execute(usersListRequest)).thenReturn(List.of(createAuth0User("test@gmail.com")));

    Mockito.when(managementService.newInstance()).thenReturn(managementApi);

    authService.changePassword(new ChangePasswordDto("oldPassword", "newPassword"));

    char[] chars = (char[]) ReflectionTestUtils.getField(newUser[0], "password");
    assertNotNull(chars);
    assertEquals("newPassword", new String(chars));
  }

  @Test
  void shouldNotThrowWhenUserIsNotFound() {
    User[] newUser = new User[1];

    UserDto userDto = new UserDto();
    userDto.setEmail("test@gmail.com");
    Mockito.when(authContext.getUser()).thenReturn(userDto);

    TokenRequest tokenRequest = Mockito.mock(TokenRequest.class);
    Mockito.when(tokenRequest.setAudience(ArgumentMatchers.any())).thenReturn(tokenRequest);
    Mockito.when(tokenRequest.setScope(ArgumentMatchers.any())).thenReturn(tokenRequest);

    Mockito.when(auth.login(ArgumentMatchers.any(), ArgumentMatchers.any(char[].class))).thenReturn(tokenRequest);

    Request<List<User>> usersListRequest = (Request<List<User>>) Mockito.mock(Request.class);

    UsersEntity usersEntity = Mockito.mock(UsersEntity.class);
    Mockito.when(usersEntity.listByEmail(ArgumentMatchers.same("test@gmail.com"), ArgumentMatchers.any())).thenReturn(usersListRequest);

    ManagementAPI managementApi = Mockito.mock(ManagementAPI.class);
    Mockito.when(managementApi.users()).thenReturn(usersEntity);

    Mockito.when(executor.execute(tokenRequest)).thenReturn(null);
    Mockito.when(executor.execute(usersListRequest)).thenReturn(new ArrayList<>());

    Mockito.when(managementService.newInstance()).thenReturn(managementApi);

    authService.changePassword(new ChangePasswordDto("oldPassword", "newPassword"));

    assertNull(newUser[0]);
  }

  @Test
  void shouldResetPassword() {
    Request<Void> voidRequest = Mockito.mock(Request.class);
    Mockito.when(auth.resetPassword(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(voidRequest);

    assertDoesNotThrow(() -> authService.resetPassword(new EmailDto("test@gmail.com")));
  }

  @Test
  void shouldReturnGoogleLoginUrl() {
    OAuth2Provider googleProvider = Mockito.mock(OAuth2Provider.class);
    Mockito.when(googleProvider.getLoginUrl()).thenReturn(new UrlDto("https://www.google.com.ua/"));

    Mockito.when(providers.get(OAuth2Connection.GOOGLE)).thenReturn(googleProvider);

    UrlDto loginUrl = authService.getLoginUrl(OAuth2Connection.GOOGLE);
    assertEquals("https://www.google.com.ua/", loginUrl.getUrl());
  }

  @Test
  void shouldReturnTokenForExistingUser() {
    TokenHolder tokenHolder = new TokenHolder("accessToken", "idToken", "refreshToken", "Bearer", 1000000, "email, profile", new Date());

    TokenRequest tokenRequest = Mockito.mock(TokenRequest.class);

    Mockito.when(auth.exchangeCode(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(tokenRequest);

    Mockito.when(executor.execute(tokenRequest)).thenReturn(tokenHolder);

    OAuth2Provider googleProvider = Mockito.mock(OAuth2Provider.class);
    Mockito.when(googleProvider.getUserInfo(ArgumentMatchers.any())).thenReturn(new OAuth2UserInfo(null, null, "test@gmail.com"));

    Mockito.when(providers.get(OAuth2Connection.GOOGLE)).thenReturn(googleProvider);

    Mockito.when(userService.findUserWithRolesByEmail("test@gmail.com")).thenReturn(Optional.of(new UserDto()));

    TokenDto tokenDto = authService.login(OAuth2Connection.GOOGLE, new OAuth2CodeDto());

    assertEquals("accessToken", tokenDto.getAccessToken());
    assertEquals("refreshToken", tokenDto.getRefreshToken());
  }

  @Test
  void shouldThrowExceptionIfUserInfoDoesNotContainEmail() {
    TokenHolder tokenHolder = new TokenHolder("accessToken", "idToken", "refreshToken", "Bearer", 1000000, "email, profile", new Date());

    TokenRequest tokenRequest = Mockito.mock(TokenRequest.class);

    Mockito.when(auth.exchangeCode(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(tokenRequest);

    Mockito.when(executor.execute(tokenRequest)).thenReturn(tokenHolder);

    OAuth2Provider googleProvider = Mockito.mock(OAuth2Provider.class);
    Mockito.when(googleProvider.getUserInfo(ArgumentMatchers.any())).thenReturn(new OAuth2UserInfo(null, null, null));

    Mockito.when(providers.get(OAuth2Connection.GOOGLE)).thenReturn(googleProvider);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> authService.login(OAuth2Connection.GOOGLE, new OAuth2CodeDto()));
    assertEquals("Cannot identify user - email is missing", exception.getMessage());
  }

  @Test
  void shouldCreateUserIfDoesNotExist() {
    UserDto[] newUser = new UserDto[1];
    TokenHolder tokenHolder = new TokenHolder("accessToken", "idToken", "refreshToken", "Bearer", 1000000, "email, profile", new Date());

    TokenRequest tokenRequest = Mockito.mock(TokenRequest.class);

    Mockito.when(auth.exchangeCode(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(tokenRequest);

    Mockito.when(executor.execute(tokenRequest)).thenReturn(tokenHolder);

    OAuth2Provider googleProvider = Mockito.mock(OAuth2Provider.class);
    Mockito.when(googleProvider.getUserInfo(ArgumentMatchers.any())).thenReturn(new OAuth2UserInfo(null, null, "test@gmail.com"));

    Mockito.when(providers.get(OAuth2Connection.GOOGLE)).thenReturn(googleProvider);

    Mockito.when(userService.findUserWithRolesByEmail("test@gmail.com")).thenReturn(Optional.empty());
    Mockito.when(userService.save(ArgumentMatchers.any())).thenAnswer(invocation -> {
      newUser[0] = invocation.getArgument(0);
      return null;
    });

    TokenDto tokenDto = authService.login(OAuth2Connection.GOOGLE, new OAuth2CodeDto());

    assertEquals("accessToken", tokenDto.getAccessToken());
    assertEquals("refreshToken", tokenDto.getRefreshToken());
    assertEquals(1, newUser[0].getRoles().size());
    assertEquals("test@gmail.com", newUser[0].getEmail());
    assertTrue(newUser[0].getRoles().contains(Role.USER.name()));
  }

  private User createAuth0User(String email) {
    Identity identity = new Identity();
    ReflectionTestUtils.setField(identity, "connection", "Username-Password-Authentication");
    List<Identity> identities = List.of(identity);
    User user = new User();
    user.setEmail(email);
    ReflectionTestUtils.setField(user, "identities", identities);
    return user;
  }
}
