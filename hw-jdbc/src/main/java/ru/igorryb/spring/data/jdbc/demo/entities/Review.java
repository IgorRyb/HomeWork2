package ru.igorryb.spring.data.jdbc.demo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("REVIEWS")
public class Review {

    @Id
    private Long id;
    private String author;
    private String text;
    private Long grade;
    private Long bookId;
    private LocalDate dateAdded;

    @PersistenceCreator
    public Review(Long id, String author, String text, Long grade, Long bookId, LocalDate dateAdded) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.grade = grade;
        this.bookId = bookId;
        this.dateAdded = dateAdded;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
