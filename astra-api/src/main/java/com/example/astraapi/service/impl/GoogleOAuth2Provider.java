package com.example.astraapi.service.impl;

import com.example.astraapi.config.GoogleProperties;
import com.example.astraapi.dto.UrlDto;
import com.example.astraapi.model.OAuth2UserInfo;
import com.example.astraapi.security.SecurityProperties;
import com.example.astraapi.service.OAuth2Provider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service("google")
@RequiredArgsConstructor
public class GoogleOAuth2Provider implements OAuth2Provider {
  private final Base64.Decoder decoder;
  private final ObjectMapper objectMapper;
  private final SecurityProperties securityProperties;
  private final GoogleProperties googleProperties;

  @Override
  public UrlDto getLoginUrl() {
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
  public OAuth2UserInfo getUserInfo(String idToken) {
    String[] chunks = idToken.split("\\.");
    if (chunks.length != 3) {
      throw new IllegalArgumentException("Invalid id token");
    }
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
