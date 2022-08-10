package com.example.astraapi.dto;

import com.example.astraapi.annotation.TrimmedLength;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenDto {
  @TrimmedLength(min = 1)
  private String refreshToken;
}
