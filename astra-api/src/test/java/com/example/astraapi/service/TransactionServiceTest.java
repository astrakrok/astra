package com.example.astraapi.service;

import com.example.astraapi.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void shouldNotRunWhenNullIsPassed() {
        assertDoesNotThrow(() -> transactionService.execute(null));
    }

    @Test
    void shouldRun() {
        assertDoesNotThrow(() -> transactionService.execute(() -> {
        }));
    }
}
