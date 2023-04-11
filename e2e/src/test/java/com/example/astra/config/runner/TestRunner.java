package com.example.astra.config.runner;

import org.junit.jupiter.api.extension.ExtensionContext;

public interface TestRunner {
    void configureEnvironment(ExtensionContext context);
}
