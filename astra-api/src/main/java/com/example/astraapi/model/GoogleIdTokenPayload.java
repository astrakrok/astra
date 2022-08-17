package com.example.astraapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoogleIdTokenPayload {
  @JsonProperty("given_name")
  private String givenName;
  @JsonProperty("family_name")
  private String familyName;
  private String email;
}
