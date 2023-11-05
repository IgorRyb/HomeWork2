package ru.otus.writer;

import ru.otus.dto.ResponseDto;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface WriterAndReader {

    void writeToFile(File file, List<ResponseDto> outputData) throws IOException;

    List<ResponseDto> readFromFile(File file) throws IOException;
}
