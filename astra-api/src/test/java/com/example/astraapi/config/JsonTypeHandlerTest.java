package com.example.astraapi.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JsonTypeHandlerTest {
    private final JsonTypeHandler handler = new JsonTypeHandler();

    @Test
    void shouldWrapJsonProcessingExceptionIntoRuntimeException() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(eq(2))).thenReturn("{value: 1");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> handler.getNullableResult(resultSet, 2));
        assertTrue(exception.getCause() instanceof JsonProcessingException);
    }

    @Test
    void shouldReturnNullableResultByResultSetAndColumnIndex() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(eq(2))).thenReturn("{\"value\": 1}");

        Object result = handler.getNullableResult(resultSet, 2);
        assertNotNull(result);
    }

    @Test
    void shouldReturnNullableResultByResultSetAndColumnName() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(eq("test"))).thenReturn("{\"value\": 1}");

        Object result = handler.getNullableResult(resultSet, "test");
        assertNotNull(result);
    }

    @Test
    void shouldReturnNullableResultByCallableStatementAndColumnIndex() throws SQLException {
        CallableStatement callableStatement = mock(CallableStatement.class);
        when(callableStatement.getString(eq(2))).thenReturn("{\"value\": 1}");

        Object result = handler.getNullableResult(callableStatement, 2);
        assertNotNull(result);
    }
}
