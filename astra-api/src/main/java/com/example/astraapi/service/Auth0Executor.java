package com.example.astraapi.service;

import com.auth0.net.Request;

public interface Auth0Executor {
  <T> T execute(Request<T> request);
}
