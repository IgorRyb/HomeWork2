package ru.otus.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvMap {

    public List<CsvDto> toList(List<ResponseDto> responseDtoList) {
        List<CsvDto> responseDtos = new ArrayList<>();
        for (ResponseDto responseDto : responseDtoList) {
            for (InfoDto infoDto : responseDto.getNestedInfo()) {
                CsvDto csvDto = new CsvDto(responseDto.getBelongNumber(), infoDto.getChatIdentifier(), infoDto.getLast(), infoDto.getSendDate(), infoDto.getText());
                responseDtos.add(csvDto);
            }
        }
        return responseDtos;
    }

    public List<ResponseDto> fromList(List<CsvDto> responseDtoList) {
        Map<String, List<CsvDto>> groupByNumber = responseDtoList.stream().collect(Collectors.groupingBy(CsvDto::getBelongNumber));
        List<ResponseDto> responseDtos = new ArrayList<>();
        for (Map.Entry<String, List<CsvDto>> entry : groupByNumber.entrySet()) {
            List<InfoDto> infoDtos = new ArrayList<>();
            entry.getValue().stream().map(CsvMap::toInfoDto).forEach(infoDtos::add);
            ResponseDto responseDto = new ResponseDto(entry.getKey(), infoDtos);
            responseDtos.add(responseDto);
        }
        return responseDtos;
    }

    public static InfoDto toInfoDto(CsvDto csvDto) {
        return new InfoDto(csvDto.getChatIdentifier(), csvDto.getLast(), csvDto.getSendDate(), csvDto.getText());
    }
}
