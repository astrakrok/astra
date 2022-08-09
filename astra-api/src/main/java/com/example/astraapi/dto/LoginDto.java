package com.example.astraapi.dto;

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
  @Pattern(regexp = "^(?=.*[a-zа-щьюяґєії])(?=.*[A-ZА-ЩЬЮЯҐЄІЇ])(?=.*\\d)[a-zа-щьюяґєіїA-ZА-ЩЬЮЯҐЄІЇ\\d\\w\\W]{8,}$")
  private String password;
}
