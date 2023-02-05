package com.example.astraapi.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@UtilityClass
public class FileUtils {
    public static InputStream getInputStream(MultipartFile file) {
        try {
            return file.getInputStream();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
