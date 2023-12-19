package ru.igorryb.spring.data.jdbc.demo.dtos;

import java.time.LocalDate;

public class ReviewDto {

    private Long id;
    private String author;
    private String text;
    private Long grade;
    private Long bookId;
    private LocalDate dateAdded;

    public ReviewDto(Long id, String author, String text, Long grade, Long bookId, LocalDate dateAdded) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.grade = grade;
        this.bookId = bookId;
        this.dateAdded = dateAdded;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getGrade() {
        return grade;
    }

    public void setGrade(Long grade) {
        this.grade = grade;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }
}
