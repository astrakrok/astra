package com.example.astraapi.dto;

import com.example.astraapi.annotation.TrimmedLength;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestSubjectDto {
  @TrimmedLength(min = 6, max = 255)
  private String title;
  @Size(min = 1)
  private List<Long> specializationIds = new ArrayList<>();
}
