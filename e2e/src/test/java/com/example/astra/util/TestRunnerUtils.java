package com.example.astra.util;

import com.example.astra.config.runner.TestRunner;
import com.example.astra.config.runner.impl.LocalTestRunner;
import com.example.astra.config.runner.impl.PipelineTestRunner;

import java.util.Map;

public class TestRunnerUtils {
    private static final String TEST_ENV = "TEST_ENV";
    private static final String LOCAL_ENV_VALUE = "local";
    private static final String PIPELINE_ENV_VALUE = "pipeline";
    private static final String ENV_VALUE = getEnvValue();
    private static final TestRunner LOCAL_TEST_RUNNER = new LocalTestRunner();
    private static final TestRunner PIPELINE_TEST_RUNNER = new PipelineTestRunner();
    private static final Map<String, TestRunner> ENV_TO_RUNNER = Map.of(
            LOCAL_ENV_VALUE, LOCAL_TEST_RUNNER,
            PIPELINE_ENV_VALUE, PIPELINE_TEST_RUNNER);

    public static TestRunner getRunner() {
        return ENV_TO_RUNNER.get(ENV_VALUE);
    }

    private static String getEnvValue() {
        String testEnv = System.getenv(TEST_ENV);
        if (LOCAL_ENV_VALUE.equalsIgnoreCase(testEnv)) {
            return LOCAL_ENV_VALUE;
        }
        return PIPELINE_ENV_VALUE;
    }
}
