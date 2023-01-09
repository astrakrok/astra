package com.example.astraapi.util;

import lombok.experimental.UtilityClass;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@UtilityClass
public class JsoupUtils {
    public static Document getDocument(String url) {
        return getDocument(url, Collections.emptyMap());
    }

    public static Document getDocument(String url, Map<String, String> queryParams) {
        try {
            Connection connection = Jsoup.connect(url);
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                connection.data(entry.getKey(), entry.getValue());
            }
            return connection.get();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
