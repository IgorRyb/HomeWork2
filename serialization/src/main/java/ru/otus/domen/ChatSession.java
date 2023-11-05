package ru.otus.domen;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class ChatSession {

    @JsonProperty("chat_id")
    private long chatId;

    @JsonProperty("chat_identifier")
    private String chatIdentifier;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("is_deleted")
    private boolean isDeleted;

    @JsonProperty("members")
    List<Member> members;

    @JsonProperty("messages")
    List<Message> messages;
}
