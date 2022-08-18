package com.example.astraapi.service;

import com.example.astraapi.dto.ChangePasswordDto;
import com.example.astraapi.dto.EmailDto;
import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.LoginDto;
import com.example.astraapi.dto.OAuth2CodeDto;
import com.example.astraapi.dto.RefreshTokenDto;
import com.example.astraapi.dto.SignUpDto;
import com.example.astraapi.dto.TokenDto;
import com.example.astraapi.dto.UrlDto;
import com.example.astraapi.meta.OAuth2Connection;

public interface AuthService {
  TokenDto login(LoginDto loginDto);

  TokenDto refreshToken(RefreshTokenDto refreshTokenDto);

  IdDto signUp(SignUpDto signUpDto);

  void changePassword(ChangePasswordDto changePasswordDto);

  void resetPassword(EmailDto emailDto);

  UrlDto getLoginUrl(OAuth2Connection connection);

  TokenDto login(OAuth2Connection connection, OAuth2CodeDto codeDto);
}
