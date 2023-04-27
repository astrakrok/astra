package com.example.astraapi.dto.auth;

import com.example.astraapi.meta.Regex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
    @Pattern(regexp = Regex.TEXT)
    private String name;
    @Pattern(regexp = Regex.TEXT)
    private String surname;
    @Min(1)
    @Max(6)
    private Integer course;
    private String school;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Pattern(regexp = Regex.PASSWORD)
    private String password;
    private Long specializationId;
}
