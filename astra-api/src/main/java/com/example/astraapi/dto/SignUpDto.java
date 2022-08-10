package com.example.astraapi.dto;

import com.example.astraapi.annotation.TrimmedLength;
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
public class SignUpDto {
  @TrimmedLength(min = 2)
  private String name;
  @TrimmedLength(min = 2)
  private String surname;
  private String course;
  private String school;
  @Email
  private String email;
  @Pattern(regexp = Regex.PASSWORD)
  private String password;
}
