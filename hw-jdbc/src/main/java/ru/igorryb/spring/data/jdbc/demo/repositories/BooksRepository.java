package ru.igorryb.spring.data.jdbc.demo.repositories;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.igorryb.spring.data.jdbc.demo.dtos.DetailedBookDto;
import ru.igorryb.spring.data.jdbc.demo.entities.Book;

import java.util.List;

@Repository
public interface BooksRepository extends ListCrudRepository<Book, Long> {

    @Query(
            "select b.id, b.title, b.genre, a.full_name as author_name, bd.description from BOOKS b " +
                    " left join AUTHORS a on b.author_id = a.id " +
                    " left join BOOKS_DETAILS bd on bd.book_id = b.id"
    )
    List<DetailedBookDto> findAllDetailedBooks();

    @Query("update books set title = :title where id = :id")
    @Modifying
    void changeTitleById(Long id, String title);

    @Query(
            "select b.id, b.title, b.genre, a.full_name as author_name, bd.description from BOOKS b " +
                    " left join AUTHORS a on b.author_id = a.id" +
                    " left join BOOKS_DETAILS bd on bd.book_id = b.id" +
                    " group by b.id limit :pageLimit offset :pageOffset"
    )
    List<DetailedBookDto> findBookWithPagination(int pageLimit, int pageOffset);

    @Query("select b.id, b.title, AVG(r.grade) as avg_rating from BOOKS b " +
            " left join REVIEWS r on r.book_id = b.id " +
            " where b.id = :id" +
            " group by b.id, b.title")
    DetailedBookDto findBookWithRating(Long id);

    @Query(
            "select b.id, b.title, AVG(r.grade) as avg_rating from BOOKS b" +
            " left join REVIEWS r on r.book_id = b.id " +
            " group by b.id, b.title" +
            " order by avg_rating DESC" +
            " limit 10"
    )
    List<DetailedBookDto> findTop10Books();
}