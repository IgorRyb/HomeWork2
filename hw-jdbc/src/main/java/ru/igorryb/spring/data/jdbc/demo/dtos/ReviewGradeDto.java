package ru.igorryb.spring.data.jdbc.demo.dtos;

public class ReviewGradeDto {

    private Long id;
    private String title;
    private Long avgRating;

    public ReviewGradeDto(Long id, String title, Long avgRating) {
        this.id = id;
        this.title = title;
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

    public Long getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Long avgRating) {
        this.avgRating = avgRating;
    }
}
