package com.example.astraapi;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;

public class TestUtils {
    private static final Random RANDOM = new Random();

    public static int nextInt(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }

    public static InputStream getResourceStream(String fileName) {
        return TestUtils.class.getClassLoader().getResourceAsStream(fileName);
    }

    public static File getResourceFile(String fileName) {
        try {
            URL url = TestUtils.class.getClassLoader().getResource(fileName);
            if (url == null) {
                throw new IllegalArgumentException("No such file");
            }
            return new File(url.toURI());
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
    }
}
