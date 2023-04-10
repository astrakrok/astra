package com.example.astra.config;

import com.example.astra.util.TestRunnerUtils;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class EnvironmentExtension implements BeforeAllCallback {
    @Override
    public void beforeAll(ExtensionContext context) {
        TestRunnerUtils.getRunner().configureEnvironment(context);
    }
}
