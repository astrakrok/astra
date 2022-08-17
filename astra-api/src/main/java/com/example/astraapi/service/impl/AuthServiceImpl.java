package com.example.astraapi.service.impl;

import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.json.mgmt.users.Identity;
import com.auth0.json.mgmt.users.User;
import com.auth0.net.Request;
import com.auth0.net.SignUpRequest;
import com.auth0.net.TokenRequest;
import com.example.astraapi.config.GoogleProperties;
import com.example.astraapi.dto.ChangePasswordDto;
import com.example.astraapi.dto.CodeDto;
import com.example.astraapi.dto.EmailDto;
import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.LoginDto;
import com.example.astraapi.dto.RefreshTokenDto;
import com.example.astraapi.dto.SignUpDto;
import com.example.astraapi.dto.TokenDto;
import com.example.astraapi.dto.UrlDto;
import com.example.astraapi.dto.UserDto;
import com.example.astraapi.exception.AlreadyExistsException;
import com.example.astraapi.exception.AuthProviderException;
import com.example.astraapi.exception.ResourceNotFoundException;
import com.example.astraapi.mapper.AuthMapper;
import com.example.astraapi.mapper.TokenMapper;
import com.example.astraapi.meta.Role;
import com.example.astraapi.model.GoogleIdTokenPayload;
import com.example.astraapi.security.SecurityProperties;
import com.example.astraapi.service.AuthContext;
import com.example.astraapi.service.AuthService;
import com.example.astraapi.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final TokenMapper tokenMapper;
  private final AuthAPI auth;
  private final SecurityProperties securityProperties;
  private final GoogleProperties googleProperties;
  private final UserService userService;
  private final AuthMapper authMapper;
  private final AuthContext authContext;
  private final ObjectMapper objectMapper;

  @Override
  public TokenDto login(LoginDto loginDto) {
    Request<TokenHolder> request = gteLoginRequest(loginDto.getEmail(), loginDto.getPassword());
    TokenHolder holder = execute(request);
    return tokenMapper.toDto(holder);
  }

  @Override
  public TokenDto refreshToken(RefreshTokenDto refreshTokenDto) {
    TokenRequest request = auth.renewAuth(refreshTokenDto.getRefreshToken())
        .setScope(securityProperties.getScope());
    TokenHolder holder = execute(request);
    return tokenMapper.toDto(holder);
  }

  @Override
  @Transactional
  public IdDto signUp(SignUpDto signUpDto) {
    userService.findUserWithRolesByEmail(signUpDto.getEmail())
        .ifPresent(this::userExists);
    SignUpRequest request = auth.signUp(
        signUpDto.getEmail(),
        signUpDto.getPassword().toCharArray(),
        securityProperties.getConnection());
    UserDto userDto = authMapper.toUserDto(signUpDto);
    userDto.getRoles().add(Role.USER.name());
    IdDto idDto = userService.save(userDto);
    execute(request);
    return idDto;
  }

  @Override
  public void changePassword(ChangePasswordDto changePasswordDto) {
    String email = authContext.getUser().getEmail();
    Request<TokenHolder> request = gteLoginRequest(email, changePasswordDto.getOldPassword());
    // We need this to check user's old password. If it's incorrect then login will fail with an exception
    execute(request);

    ManagementAPI managementApi = createManagementApi();
    User user = getAuth0UserByEmail(managementApi, email);
    changeUserPassword(managementApi, user, changePasswordDto.getNewPassword());
  }

  @Override
  public void resetPassword(EmailDto emailDto) {
    Request<Void> request = auth.resetPassword(emailDto.getEmail(), securityProperties.getConnection());
    execute(request);
  }

  @Override
  public UrlDto getGoogleLoginUrl() {
    return new UrlDto(
        securityProperties.getIssuerUri() +
            "authorize" +
            "?audience=" + securityProperties.getAudience() +
            "&access_type=offline" +
            "&response_type=code" +
            "&client_id=" + securityProperties.getClientId() +
            "&connection=" + googleProperties.getConnection() +
            "&redirect_uri=" + googleProperties.getRedirectUri() +
            "&connection_scope=" + googleProperties.getScope() +
            "&scope=" + securityProperties.getScope()
    );
  }

  @Override
  public TokenDto googleLogin(CodeDto codeDto) {
    TokenRequest request = auth.exchangeCode(codeDto.getCode(), "http://localhost:3000/google/callback");
    TokenHolder tokenHolder = execute(request);
    TokenDto tokenDto = tokenMapper.toDto(tokenHolder);
    UserDto user = getUserInfoFromToken(tokenHolder.getIdToken());
    if (user.getEmail() == null) {
      throw new IllegalArgumentException("Cannot identify user - email is missing");
    }
    Optional<UserDto> possibleUser = userService.findUserWithRolesByEmail(user.getEmail());
    if (possibleUser.isEmpty()) {
      user.getRoles().add(Role.USER.name());
      userService.save(user);
    }
    return tokenDto;
  }

  private User getAuth0UserByEmail(ManagementAPI managementAPI, String email) {
    Request<List<User>> request = managementAPI.users().listByEmail(email, null);
    List<User> users = execute(request);
    return users.stream()
        .filter(user -> securityProperties.getConnection().equals(getAuth0UserConnection(user)))
        .findFirst()
        .orElse(null);
  }

  private String getAuth0UserConnection(User user) {
    return user.getIdentities().stream()
        .map(Identity::getConnection)
        .findFirst()
        .orElseThrow(() -> new ResourceNotFoundException("User with email " + user.getEmail() + " was not found"));
  }

  private void changeUserPassword(ManagementAPI managementApi, User user, String newPassword) {
    if (user == null) {
      return;
    }
    User newUser = new User();
    newUser.setPassword(newPassword.toCharArray());
    Request<User> request = managementApi.users().update(user.getId(), newUser);
    execute(request);
  }

  private ManagementAPI createManagementApi() {
    TokenRequest request = auth.requestToken(securityProperties.getIssuerUri() + "api/v2/");
    TokenHolder holder = execute(request);
    return new ManagementAPI(
        securityProperties.getIssuerUri(),
        holder.getAccessToken()
    );
  }

  private <T> T execute(Request<T> request) {
    try {
      return request.execute();
    } catch (Auth0Exception exception) {
      throw new AuthProviderException("An unexpected error occurred");
    }
  }

  private Request<TokenHolder> gteLoginRequest(String email, String password) {
    return auth
        .login(email, password.toCharArray())
        .setAudience(securityProperties.getAudience())
        .setScope(securityProperties.getScope());
  }

  private void userExists(UserDto userDto) {
    throw new AlreadyExistsException("User with such email already exists");
  }

  private UserDto getUserInfoFromToken(String idToken) {
    String[] chunks = idToken.split("\\.");
    String payload = decodeIdTokenChunk(chunks[1]);
    GoogleIdTokenPayload googleIdTokenPayload = parseGoogleIdTokenPayload(payload);
    return authMapper.toUserDto(googleIdTokenPayload);
  }

  private String decodeIdTokenChunk(String chunk) {
    Base64.Decoder decoder = Base64.getUrlDecoder();
    return new String(decoder.decode(chunk));
  }

  private GoogleIdTokenPayload parseGoogleIdTokenPayload(String payload) {
    try {
      return objectMapper.readValue(payload, GoogleIdTokenPayload.class);
    } catch (JsonProcessingException exception) {
      throw new IllegalArgumentException("Unable to parse google id token");
    }
  }
}
