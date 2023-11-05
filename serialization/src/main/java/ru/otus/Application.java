package ru.otus;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.domen.MessagesReport;
import ru.otus.dto.ResponseDto;
import ru.otus.writer.SortingData;
import ru.otus.writer.WriterAndReader;
import ru.otus.writer.impl.CsvWriterAndReader;
import ru.otus.writer.impl.JsonWriterAndReader;
import ru.otus.writer.impl.XmlWriterAndReader;
import ru.otus.writer.impl.YamlWriterAndReader;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;

public class Application {

    private static final String directory = "out";

    public static void main(String[] args) throws IOException {
        URL inputJson = getResourceUrlFile("in/sms-348536-95ecd0.json");
        ObjectMapper objectMapper = new ObjectMapper();
        MessagesReport messagesReport = objectMapper.readValue(inputJson, MessagesReport.class);
        SortingData sortingData = new SortingData();
        List<ResponseDto> outputData =  sortingData.compileData(messagesReport);

        executeCsvFile("out.csv", outputData);
        executeJsonFile("out.json", outputData);
        executeXmlFile("out.xml", outputData);
        executeYamlFile("out.yaml", outputData);
    }

    public static void executeJsonFile(String fileName, List<ResponseDto> outputData) throws IOException {
        File file = getFile(fileName);
        WriterAndReader jsonWriterAndReader = new JsonWriterAndReader();
        jsonWriterAndReader.writeToFile(file, outputData);
        System.out.println("Read json: \n" + jsonWriterAndReader.readFromFile(file));
    }

    public static void executeXmlFile(String fileName, List<ResponseDto> outputData) throws IOException {
        File file = getFile(fileName);
        WriterAndReader xmlWriterAndReader = new XmlWriterAndReader();
        xmlWriterAndReader.writeToFile(file, outputData);
        System.out.println("Read xml: \n" + xmlWriterAndReader.readFromFile(file));
    }

    public static void executeYamlFile(String fileName, List<ResponseDto> outputData) throws IOException {
        File file = getFile(fileName);
        WriterAndReader yamlWriterAndReader = new YamlWriterAndReader();
        yamlWriterAndReader.writeToFile(file, outputData);
        System.out.println("Read yaml: \n" + yamlWriterAndReader.readFromFile(file));
    }

    public static void executeCsvFile(String fileName, List<ResponseDto> outputData) throws IOException {
        File file = getFile(fileName);
        WriterAndReader csvWriterAndReader = new CsvWriterAndReader();
        csvWriterAndReader.writeToFile(file, outputData);
        System.out.println("Read csv: \n" + csvWriterAndReader.readFromFile(file));
    }

    public static URL getResourceUrlFile(String filePath) {
        return ClassLoader.getSystemResource(filePath);
    }

    public static File getFile(String fileName) {
        File file = null;
        try {
            file = Path.of(ClassLoader.getSystemResource(directory).toURI()).resolve(Path.of(fileName)).toFile();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return file;
    }
}