package com.example.astraapi.dto.auth;

import com.example.astraapi.meta.Regex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto {
  @NotNull
  @Pattern(regexp = Regex.PASSWORD)
  private String oldPassword;
  @NotNull
  @Pattern(regexp = Regex.PASSWORD)
  private String newPassword;
}
