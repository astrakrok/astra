package com.example.astraapi.validation.impl;

import com.example.astraapi.config.FileProperties;
import com.example.astraapi.dto.MessageDto;
import com.example.astraapi.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
        if (StringUtils.isBlank(filename)) {
            throw new IllegalArgumentException("File name cannot be blank");
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
