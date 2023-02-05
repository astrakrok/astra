package com.example.astraapi.factory.impl;

import com.example.astraapi.factory.CsvFactory;
import com.example.astraapi.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class CsvFactoryImpl implements CsvFactory {
    @Override
    public CSVParser newParser(MultipartFile file, CSVFormat format) throws IOException {
        return new CSVParser(new InputStreamReader(FileUtils.getInputStream(file), StandardCharsets.UTF_8), format);
    }

    @Override
    public CSVPrinter newPrinter(Appendable appendable, CSVFormat format) throws IOException {
        return new CSVPrinter(appendable, format);
    }
}
