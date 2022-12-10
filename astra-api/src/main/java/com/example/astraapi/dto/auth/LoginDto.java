package com.example.astraapi.dto.auth;

import com.example.astraapi.meta.Regex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
  @Email
  private String email;
  @Pattern(regexp = Regex.PASSWORD)
  private String password;
}
