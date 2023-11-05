package ru.otus.domen;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class Member {

    @JsonProperty("first")
    private String first;

    @JsonProperty("handle_id")
    private long handleId;

    @JsonProperty("image_path")
    private String imagePath;

    @JsonProperty("last")
    private String last;

    @JsonProperty("middle")
    private String middle;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("service")
    private String service;

    @JsonProperty("thumb_path")
    private String thumbPath;
}
