package ru.otus.writer.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.writer.WriterAndReader;
import ru.otus.dto.ResponseDto;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonWriterAndReader implements WriterAndReader {

    @Override
    public void writeToFile(File file, List<ResponseDto> outputData) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, outputData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ResponseDto> readFromFile(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ResponseDto> responseDtoList= objectMapper.readValue(file, new TypeReference<>() {
        });
        return responseDtoList;
    }
}
