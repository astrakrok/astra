package com.example.astra.config.runner.impl;

import com.codeborne.selenide.Configuration;
import com.example.astra.config.runner.TestRunner;
import org.junit.jupiter.api.extension.ExtensionContext;

public class LocalTestRunner implements TestRunner {
    private static final String FRONTEND_URL = "http://localhost:3001";

    @Override
    public void configureEnvironment(ExtensionContext context) {
        Configuration.baseUrl = FRONTEND_URL;
        Configuration.timeout = 15_000;
        Configuration.browserSize = "1920x1080";
    }
}
