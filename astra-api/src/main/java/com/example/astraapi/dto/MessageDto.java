package com.example.astraapi.dto;

import com.example.astraapi.annotation.TrimmedLength;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
  @NotNull
  @TrimmedLength(min = 10, max = 100)
  private String title;
  @NotNull
  @TrimmedLength(min = 10, max = 900)
  private String text;
  private MultipartFile file;
}
