package com.example.astraapi.service;

import com.auth0.client.mgmt.ManagementAPI;

public interface Auth0ManagementService {
  ManagementAPI newInstance();
}
