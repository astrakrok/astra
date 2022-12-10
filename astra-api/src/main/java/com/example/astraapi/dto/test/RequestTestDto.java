package com.example.astraapi.dto.test;

import com.example.astraapi.annotation.TrimmedLength;
import com.example.astraapi.dto.test.variant.TestVariantDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
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
  @NotNull
  @TrimmedLength(min = 10)
  private String question;
  @NotNull
  @TrimmedLength(min = 10)
  private String comment;
  private String questionSvg;
  private String commentSvg;
  @Size(min = 1)
  private List<TestVariantDto> variants = new ArrayList<>();
  @Size(min = 1)
  private Set<Long> subjectIds = new HashSet<>();
}
