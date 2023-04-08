package com.example.astraapi.service.impl;

import com.example.astraapi.meta.ExecutionProfile;
import com.example.astraapi.service.JsoupService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Profile(ExecutionProfile.PRODUCTION)
public class JsoupServiceImpl implements JsoupService {
    @Override
    public Document getDocument(String url) {
        return getDocument(url, Collections.emptyMap());
    }

    @Override
    public Document getDocument(String url, Map<String, String> queryParams) {
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
