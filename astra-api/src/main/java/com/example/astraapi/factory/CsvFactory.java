package com.example.astraapi.factory;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CsvFactory {
    CSVParser newParser(MultipartFile file, CSVFormat format) throws IOException;

    CSVPrinter newPrinter(Appendable appendable, CSVFormat format) throws IOException;
}
