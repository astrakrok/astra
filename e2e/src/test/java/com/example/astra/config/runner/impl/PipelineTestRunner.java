package com.example.astra.config.runner.impl;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.example.astra.config.runner.TestRunner;
import com.example.astra.util.TestContainerUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.DockerComposeContainer;

import java.io.File;

public class PipelineTestRunner implements TestRunner {
    private static final String FRONTEND_SERVICE = "frontend_1";
    private static final String FRONTEND_URL = "http://frontend:3000";
    private static final String CHROME_IMAGE = "selenium/standalone-chrome-debug:3.141.59";
    private static final DockerComposeContainer<?> dockerComposeContainer = new DockerComposeContainer<>(new File("../docker-compose.yml"))
            .withLocalCompose(true);
    private static final BrowserWebDriverContainer<?> chromeContainer = new BrowserWebDriverContainer<>(CHROME_IMAGE)
            .withCapabilities(new ChromeOptions())
            .withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.SKIP, null);

    @Override
    public void configureEnvironment(ExtensionContext context) {
        Configuration.baseUrl = FRONTEND_URL;
        Configuration.timeout = 20_000;
        Configuration.browserSize = "1920x1080";

        dockerComposeContainer.start();

        TestContainerUtils.linkContainers(dockerComposeContainer, chromeContainer, FRONTEND_SERVICE);

        chromeContainer.start();
        chromeContainer.getWebDriver().manage().window().setSize(new Dimension(1920, 1080));
        WebDriverRunner.setWebDriver(chromeContainer.getWebDriver());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            dockerComposeContainer.stop();
            chromeContainer.stop();
        }));
    }
}
