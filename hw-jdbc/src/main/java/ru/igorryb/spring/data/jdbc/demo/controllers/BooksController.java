package ru.igorryb.spring.data.jdbc.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.igorryb.spring.data.jdbc.demo.dtos.*;
import ru.igorryb.spring.data.jdbc.demo.services.BooksService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BooksController {
    private final BooksService booksService;

    @Autowired
    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @PatchMapping("/{id}/title")
    public void updateTitleById(@PathVariable Long id, @RequestParam String value) {
        booksService.updateTitleById(id, value);
    }

    @GetMapping
    public SimplestPageDto<PageDto> findAllBooks(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "1") int pageSize) {
        return new SimplestPageDto<>(Collections.singletonList(booksService.findBooksWithPagination(page, pageSize)));
    }

    @GetMapping("/{id}/rating")
    public ReviewGradeDto findBookWithRating(@PathVariable Long id) {
        return booksService.findBookWithRating(id);
    }

    @GetMapping("/top10")
    public List<TopReviewDto> findTop10Books() {
        return booksService.findTop10Books();
    }
}
