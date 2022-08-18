package com.example.astraapi.service;

import com.example.astraapi.dto.UrlDto;
import com.example.astraapi.model.OAuth2UserInfo;

public interface OAuth2Provider {
  UrlDto getLoginUrl();

  OAuth2UserInfo getUserInfo(String idToken);
}
