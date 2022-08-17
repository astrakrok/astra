package com.example.astraapi.service;

import com.example.astraapi.dto.ChangePasswordDto;
import com.example.astraapi.dto.CodeDto;
import com.example.astraapi.dto.EmailDto;
import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.LoginDto;
import com.example.astraapi.dto.RefreshTokenDto;
import com.example.astraapi.dto.SignUpDto;
import com.example.astraapi.dto.TokenDto;
import com.example.astraapi.dto.UrlDto;

public interface AuthService {
  TokenDto login(LoginDto loginDto);

  TokenDto refreshToken(RefreshTokenDto refreshTokenDto);

  IdDto signUp(SignUpDto signUpDto);

  void changePassword(ChangePasswordDto changePasswordDto);

  void resetPassword(EmailDto emailDto);

  UrlDto getGoogleLoginUrl();

  TokenDto googleLogin(CodeDto codeDto);
}
