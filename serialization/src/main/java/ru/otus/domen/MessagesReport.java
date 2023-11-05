package ru.otus.domen;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class MessagesReport {

    @JsonProperty("chat_sessions")
    private List<ChatSession> chatSessions;
}
