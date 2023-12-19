package ru.igorryb.spring.data.jdbc.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.igorryb.spring.data.jdbc.demo.dtos.DetailedBookDto;
import ru.igorryb.spring.data.jdbc.demo.dtos.PageDto;
import ru.igorryb.spring.data.jdbc.demo.dtos.ReviewGradeDto;
import ru.igorryb.spring.data.jdbc.demo.dtos.TopReviewDto;
import ru.igorryb.spring.data.jdbc.demo.repositories.BooksRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public void updateTitleById(Long id, String newTitle) {
        booksRepository.changeTitleById(id, newTitle);
    }

    @Transactional(readOnly = true)
    public PageDto findBooksWithPagination(int page, int pageSize) {
        int pageOffset = page * pageSize;
        try {
            List<DetailedBookDto> books = booksRepository.findBookWithPagination(pageSize, pageOffset);
            return new PageDto(books, page, pageSize);
        } catch (RuntimeException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public ReviewGradeDto findBookWithRating(Long id) {
        var book = booksRepository.findBookWithRating(id);
        return new ReviewGradeDto(book.getId(), book.getTitle(), book.getAvgRating());
    }

    @Transactional(readOnly = true)
    public List<TopReviewDto> findTop10Books() {
        List<TopReviewDto> topReviewDto = new ArrayList<>();
        booksRepository.findTop10Books()
                .stream()
                .forEach(book -> topReviewDto.add(new TopReviewDto(book.getId(), book.getTitle())));
        return topReviewDto;
    }
}
