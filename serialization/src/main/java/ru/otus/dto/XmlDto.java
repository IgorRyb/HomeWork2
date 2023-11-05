package ru.otus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@JacksonXmlRootElement(localName = "reports")
@AllArgsConstructor
@Data
public class XmlDto {

    @JacksonXmlElementWrapper(useWrapping=false)
    @JsonProperty("report")
    private List<ResponseDto> responseDtoList;
}
