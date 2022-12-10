package com.example.astraapi.dto.auth;

import com.example.astraapi.annotation.TrimmedLength;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenDto {
  @NotNull
  @TrimmedLength(min = 1)
  private String refreshToken;
}
