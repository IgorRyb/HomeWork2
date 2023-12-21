package ru.igorryb.spring.data.jdbc.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.igorryb.spring.data.jdbc.demo.entities.Review;
import ru.igorryb.spring.data.jdbc.demo.repositories.ReviewsRepository;

import java.util.List;

@Service
public class ReviewsService {

    private final ReviewsRepository reviewsRepository;

    @Autowired
    public ReviewsService(ReviewsRepository reviewsRepository) {
        this.reviewsRepository = reviewsRepository;
    }

    @Transactional
    public void addReview(Review review) {
        if ((review.getGrade()) > 10 && (review.getGrade() < 1)) {
            throw new RuntimeException();
        }
        reviewsRepository.save(review);
    }

    @Transactional(readOnly = true)
    public List<Review> findAllReviews() {
        return reviewsRepository.findAll();
    }

}
