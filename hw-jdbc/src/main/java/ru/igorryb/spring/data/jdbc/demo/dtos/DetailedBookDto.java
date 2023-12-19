package ru.igorryb.spring.data.jdbc.demo.dtos;

public class DetailedBookDto {
    private Long id;
    private String title;
    private Genre genre;
    private String authorName;
    private String description;
    private Long rating;
    private Long avgRating;

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Long getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Long avgRating) {
        this.avgRating = avgRating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DetailedBookDto() {
    }
}