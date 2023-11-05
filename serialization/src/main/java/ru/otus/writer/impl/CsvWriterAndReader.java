package ru.otus.writer.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import ru.otus.dto.CsvDto;
import ru.otus.dto.CsvMap;
import ru.otus.writer.WriterAndReader;
import ru.otus.dto.ResponseDto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvWriterAndReader implements WriterAndReader {

    @Override
    public void writeToFile(File file, List<ResponseDto> outputData) {
        CsvMap csvMap = new CsvMap();

        List<CsvDto> csvDtos = csvMap.toList(outputData);

        CsvMapper csvMapper = new CsvMapper();
        csvMapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        CsvSchema csvSchema = csvMapper.schemaFor(CsvDto.class)
                .withColumnSeparator(',')
                .withoutQuoteChar()
                .withHeader();
        ObjectWriter writer = csvMapper.writer(csvSchema);
        try {
            writer.writeValue(new FileWriter(file), csvDtos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ResponseDto> readFromFile(File file) {
        List<ResponseDto> responseDtos;
        CsvMap csvMap = new CsvMap();
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        CsvSchema csvSchema = csvMapper.schemaFor(CsvDto.class)
                .withColumnSeparator(',')
                .withoutQuoteChar()
                .withHeader();

        try {
            MappingIterator<CsvDto> csvDtoMappingIterator = csvMapper
                    .readerWithSchemaFor(CsvDto.class)
                    .with(csvSchema)
                    .readValues(file);

            List<CsvDto> csvDtos = csvDtoMappingIterator.readAll();
            responseDtos = csvMap.fromList(csvDtos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return responseDtos;
    }
}
