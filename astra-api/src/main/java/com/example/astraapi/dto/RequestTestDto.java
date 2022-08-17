package com.example.astraapi.dto;

import com.example.astraapi.annotation.TrimmedLength;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestTestDto {
  private Long id;
  @TrimmedLength(min = 10)
  private String question;
  @TrimmedLength(min = 10)
  private String comment;
  @Size(min = 1)
  private List<TestVariantDto> variants = new ArrayList<>();
  @Size(min = 1)
  private Set<Long> subjectIds = new HashSet<>();
}
