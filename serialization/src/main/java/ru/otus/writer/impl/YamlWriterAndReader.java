package ru.otus.writer.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import ru.otus.writer.WriterAndReader;
import ru.otus.dto.ResponseDto;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class YamlWriterAndReader implements WriterAndReader {

    @Override
    public void writeToFile(File file, List<ResponseDto> outputData) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        objectMapper.writeValue(file, outputData);
    }

    @Override
    public List<ResponseDto> readFromFile(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.findAndRegisterModules();
        List<ResponseDto> responseDtos;
        responseDtos = objectMapper.readValue(file, new TypeReference<>() {
        });
        return responseDtos;
    }
}
