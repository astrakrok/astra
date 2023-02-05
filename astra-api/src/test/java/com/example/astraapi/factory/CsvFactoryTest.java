package com.example.astraapi.factory;

import com.example.astraapi.TestUtils;
import com.example.astraapi.factory.impl.CsvFactoryImpl;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class CsvFactoryTest {
    @InjectMocks
    private CsvFactoryImpl csvFactory;

    @Test
    void shouldReturnNewCsvParser() throws IOException {
        MockMultipartFile file = new MockMultipartFile("data", "import-empty.csv", "text/plain", TestUtils.getResourceStream("transfer/import/import-empty.csv"));
        CSVFormat format = CSVFormat.Builder.create().build();

        CSVParser parser = csvFactory.newParser(file, format);

        assertNotNull(parser);
    }
}
