package com.example.astra.config;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.database.rider.core.api.dataset.DataSet;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseTest {
    @BeforeEach
    @DataSet(
            cleanBefore = true,
            executeScriptsBefore = "restart-sequences.sql",
            skipCleaningFor = "flyway_schema_history")
    public void beforeEach() {
        SelenideLogger.addListener(
                "allure",
                new AllureSelenide().savePageSource(false));
    }

    @AfterEach
    public void afterEach() {
        SelenideLogger.removeListener("allure");
        int windowAmount = WebDriverRunner.getWebDriver().getWindowHandles().size();
        for (int i = 0; i < windowAmount - 1; i++) {
            Selenide.closeWindow();
            switchToExistedWindow();
        }
        if (WebDriverRunner.getWebDriver().getWindowHandles().size() == 1) {
            switchToExistedWindow();
        }
        Selenide.clearBrowserCookies();
    }

    protected final Connection getConnection() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5433/astra");
        config.setUsername("postgres");
        config.setPassword("postgres");
        return new HikariDataSource(config).getConnection();
    }

    private void switchToExistedWindow() {
        String window = WebDriverRunner.getWebDriver().getWindowHandles().iterator().next();
        WebDriverRunner.getWebDriver().switchTo().window(window);
    }
}
