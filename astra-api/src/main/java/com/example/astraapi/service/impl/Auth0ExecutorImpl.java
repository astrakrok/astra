package com.example.astraapi.service.impl;

import com.auth0.exception.Auth0Exception;
import com.auth0.net.Request;
import com.example.astraapi.exception.AuthProviderException;
import com.example.astraapi.service.Auth0Executor;
import org.springframework.stereotype.Service;

@Service
public class Auth0ExecutorImpl implements Auth0Executor {
  @Override
  public <T> T execute(Request<T> request) {
    try {
      return request.execute();
    } catch (Auth0Exception exception) {
      throw new AuthProviderException("An unexpected error occurred", exception);
    }
  }
}
