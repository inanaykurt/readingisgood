package com.retail.readingisgood.repository;

import com.retail.readingisgood.model.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface BookRepository extends PagingAndSortingRepository<Book,Long> {


    @Query(
            value =
                    "SELECT B.* FROM BOOK B WHERE B.id = :id",
            nativeQuery = true)
    Book getBook(@Param("id") Long id);

    @Query(
            value =
                    "SELECT B.* FROM BOOK B WHERE B.name = :name AND B.author = :author",
            nativeQuery = true)
    Book getBookByName(@Param("name") String name, @Param("author") String author);

    @Modifying
    @Query(
            value =
                    "UPDATE BOOK B SET B.BOOK_COUNT = :bookCount WHERE B.ID = :id ",
            nativeQuery = true)
    Integer updateStockOfBook(@Param("bookCount") long bookCount, @Param("id") long id);

    @Query(
            value =
                    "SELECT B.* FROM BOOK B WHERE B.name = :bookName",
            nativeQuery = true)
    Book findBookByName(String bookName);

    @Modifying
    @Query(
            value =
                    "UPDATE BOOK B SET B.BOOK_COUNT = :bookCount, " +
                            "B.PRICE = :price " +
                            "WHERE B.ID = :id",
            nativeQuery = true)
    int update(@Param("id") long id,
               @Param("bookCount") long bookCount,
               @Param("price") double price);
}
