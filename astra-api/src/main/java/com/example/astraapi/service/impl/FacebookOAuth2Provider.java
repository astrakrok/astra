package com.example.astraapi.service.impl;

import com.example.astraapi.config.FacebookProperties;
import com.example.astraapi.dto.UrlDto;
import com.example.astraapi.model.OAuth2UserInfo;
import com.example.astraapi.security.SecurityProperties;
import com.example.astraapi.service.OAuth2Provider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service("facebook")
@RequiredArgsConstructor
public class FacebookOAuth2Provider implements OAuth2Provider {
  private final Base64.Decoder decoder;
  private final ObjectMapper objectMapper;
  private final SecurityProperties securityProperties;
  private final FacebookProperties facebookProperties;

  @Override
  public UrlDto getLoginUrl() {
    return new UrlDto(
        securityProperties.getIssuerUri() +
            "authorize" +
            "?audience=" + securityProperties.getAudience() +
            "&response_type=code" +
            "&client_id=" + securityProperties.getClientId() +
            "&connection=" + facebookProperties.getConnection() +
            "&redirect_uri=" + facebookProperties.getRedirectUri() +
            "&connection_scope=" + facebookProperties.getScope() +
            "&scope=" + securityProperties.getScope()
    );
  }

  @Override
  public OAuth2UserInfo getUserInfo(String idToken) {
    String[] chunks = idToken.split("\\.");
    String payload = decodeIdTokenChunk(chunks[1]);
    return parseTokenPayload(payload);
  }

  private String decodeIdTokenChunk(String chunk) {
    return new String(decoder.decode(chunk));
  }

  private OAuth2UserInfo parseTokenPayload(String payload) {
    try {
      return objectMapper.readValue(payload, OAuth2UserInfo.class);
    } catch (JsonProcessingException exception) {
      throw new IllegalArgumentException("Unable to parse id token");
    }
  }
}
