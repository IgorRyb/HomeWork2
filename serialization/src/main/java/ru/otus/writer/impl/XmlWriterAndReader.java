package ru.otus.writer.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ru.otus.dto.XmlDto;
import ru.otus.writer.WriterAndReader;
import ru.otus.dto.ResponseDto;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XmlWriterAndReader implements WriterAndReader {

    @Override
    public void writeToFile(File file, List<ResponseDto> outputData) throws IOException {
        XmlDto xmlDto = new XmlDto(outputData);
        new XmlMapper().writerWithDefaultPrettyPrinter().writeValue(file, xmlDto);
    }

    @Override
    public List<ResponseDto> readFromFile(File file) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        List<ResponseDto> responseDtosList = xmlMapper.readValue(file, new TypeReference<>() {
        });
        return responseDtosList;
    }
}
