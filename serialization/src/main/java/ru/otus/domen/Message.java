package ru.otus.domen;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@ToString
public class Message {

    @JsonProperty("ROWID")
    private long rowId;

    @JsonProperty("attributedBody")
    private String attributedBody;

    @JsonProperty("belong_number")
    private String belongNumber;

    @JsonProperty("date")
    private long date;

    @JsonProperty("date_read")
    private long dateRead;

    @JsonProperty("guid")
    private String guid;

    @JsonProperty("handle_id")
    private long handleId;

    @JsonProperty("has_dd_results")
    private boolean hasDdResults;

    @JsonProperty("is_deleted")
    private boolean isDeleted;

    @JsonProperty("is_from_me")
    private boolean isFromMe;

    @JsonProperty("send_date")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime sendDate;

    @JsonProperty("send_status")
    private int sendStatus;

    @JsonProperty("service")
    private String service;

    @JsonProperty("text")
    private String text;
}
