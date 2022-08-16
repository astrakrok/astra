package com.example.astraapi.validator.impl;

import com.example.astraapi.config.FileProperties;
import com.example.astraapi.dto.MessageDto;
import com.example.astraapi.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageValidator implements Validator<MessageDto> {
  private final FileProperties fileProperties;

  @Override
  public void validate(MessageDto model) {
    if (model.getFile() == null) {
      return;
    }
    String filename = model.getFile().getOriginalFilename();
    if (filename == null) {
      throw new IllegalArgumentException("File name cannot be null");
    }
    String extension = getFileExtension(model.getFile().getOriginalFilename());
    fileProperties.getAllowedExtensions().stream()
        .filter(allowedExtension -> allowedExtension.equalsIgnoreCase(extension))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Invalid extension"));
  }

  private String getFileExtension(String filename) {
    int lastDotIndex = filename.lastIndexOf('.');
    return filename.substring(lastDotIndex + 1);
  }
}
