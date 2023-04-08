package com.example.astraapi.mock;

import com.example.astraapi.meta.ExecutionProfile;
import com.example.astraapi.service.JsoupService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

@Service
@Profile(ExecutionProfile.INTEGRATION_TEST)
public class JsoupServiceMock implements JsoupService {
    @Override
    public Document getDocument(String fileName) {
        try {
            URL url = JsoupServiceMock.class.getClassLoader().getResource(String.format("transfer/import/%s", fileName));
            if (url == null) {
                throw new IllegalArgumentException("No such file");
            }
            return Jsoup.parse(new File(url.toURI()));
        } catch (URISyntaxException | IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Document getDocument(String url, Map<String, String> queryParams) {
        return null;
    }
}
