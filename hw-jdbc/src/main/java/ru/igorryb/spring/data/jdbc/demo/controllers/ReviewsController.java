package ru.igorryb.spring.data.jdbc.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.igorryb.spring.data.jdbc.demo.dtos.ReviewDto;
import ru.igorryb.spring.data.jdbc.demo.entities.Review;
import ru.igorryb.spring.data.jdbc.demo.services.ReviewsService;
import ru.igorryb.spring.data.jdbc.demo.dtos.SimplestPageDto;

import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewsController {

    private final ReviewsService reviewsService;

    private static final Function<Review, ReviewDto> MAP_TO_DTO_FUNCTION = c -> new ReviewDto(c.getId(),
            c.getAuthor(), c.getText(),c.getGrade(), c.getBookId(), c.getDateAdded());

    @Autowired
    public ReviewsController(ReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }

    @GetMapping
    public SimplestPageDto<ReviewDto> findAllReviews() {
        return new SimplestPageDto<>(reviewsService.findAllReviews()
                .stream()
                .map(MAP_TO_DTO_FUNCTION)
                .collect(Collectors.toList()));
    }

    @PostMapping("/add")
    public void addReview(@RequestBody Review review) {
        reviewsService.addReview(review);
    }
}
