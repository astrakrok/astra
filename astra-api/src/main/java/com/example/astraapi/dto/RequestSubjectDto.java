package com.example.astraapi.dto;

import com.example.astraapi.annotation.TrimmedLength;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestSubjectDto {
  @NotNull
  @TrimmedLength(min = 6, max = 255)
  private String title;
  @Size(min = 1)
  private Set<Long> specializationIds = new HashSet<>();
}
