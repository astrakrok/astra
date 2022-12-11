package com.example.astraapi.service.impl;

import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.TokenRequest;
import com.example.astraapi.security.SecurityProperties;
import com.example.astraapi.service.Auth0Executor;
import com.example.astraapi.service.Auth0ManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Auth0ManagementServiceImpl implements Auth0ManagementService {
  private final AuthAPI auth;
  private final SecurityProperties securityProperties;
  private final Auth0Executor executor;

  @Override
  public ManagementAPI newInstance() {
    TokenRequest request = auth.requestToken(securityProperties.getIssuerUri() + "api/v2/");
    TokenHolder holder = executor.execute(request);
    return new ManagementAPI(
        securityProperties.getIssuerUri(),
        holder.getAccessToken()
    );
  }
}
