package com.example.astraapi.service;

import org.jsoup.nodes.Document;

import java.util.Map;

public interface JsoupService {
    Document getDocument(String url);

    Document getDocument(String url, Map<String, String> queryParams);
}
