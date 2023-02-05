package com.example.astraapi.service.impl;

import com.example.astraapi.meta.ImportSource;
import com.example.astraapi.model.importing.ImportResult;
import com.example.astraapi.model.importing.ImportTest;
import com.example.astraapi.model.importing.ImportVariant;
import com.example.astraapi.service.WebImporter;
import com.example.astraapi.util.JsoupUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Qualifier("testingUkrWebImporter")
public class TestingUkrWebImporterImpl implements WebImporter {
    @Override
    public ImportResult importTests(String url) {
        Document document = JsoupUtils.getDocument(url);
        String title = document.title();
        List<String> urls = document.select(".page-link").stream()
                .map(element -> element.absUrl("href"))
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());
        urls.add(url);
        List<ImportTest> tests = urls.stream()
                .map(this::parseTests)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return ImportResult.builder()
                .source(ImportSource.TESTING_UKR_WEB)
                .sourceTitle(StringUtils.defaultIfBlank(title, null))
                .tests(tests)
                .details(Map.of("url", url))
                .build();
    }

    private List<ImportTest> parseTests(String url) {
        Document document = JsoupUtils.getDocument(url);
        return document.select(".text-justify").stream()
                .map(this::parseTest)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private ImportTest parseTest(Element element) {
        String question = element.text();
        List<ImportVariant> variants = element.siblingElements().stream()
                .filter(child -> !StringUtils.contains(child.attr("class"), "text-justify"))
                .map(this::parseVariant)
                .collect(Collectors.toList());
        return ImportTest.builder()
                .question(question)
                .variants(variants)
                .subjects(new ArrayList<>())
                .build();
    }

    private ImportVariant parseVariant(Element element) {
        String correctness = element.child(0).text().strip();
        String title = element.child(1).text().strip();
        return ImportVariant.builder()
                .title(title)
                .correct(!StringUtils.equals("0%", correctness))
                .build();
    }
}
