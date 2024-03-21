package ru.flamexander.reactive.service.exceptions;

import java.time.LocalDateTime;

public class ErrorDto {
    private String code;
    private LocalDateTime date;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public ErrorDto() {
    }

    public ErrorDto(String code) {
        this.code = code;
        this.date = LocalDateTime.now();
    }
}
