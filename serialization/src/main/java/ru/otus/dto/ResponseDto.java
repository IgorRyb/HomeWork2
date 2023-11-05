package ru.otus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ResponseDto {

    @JsonProperty("belong_number")
    private String belongNumber;

    @JsonProperty("info")
    private List<InfoDto> nestedInfo;
}
