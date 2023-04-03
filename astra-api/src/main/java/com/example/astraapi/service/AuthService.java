package com.example.astraapi.service;

import com.example.astraapi.dto.EmailDto;
import com.example.astraapi.dto.IdDto;
import com.example.astraapi.dto.UrlDto;
import com.example.astraapi.dto.auth.*;
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
