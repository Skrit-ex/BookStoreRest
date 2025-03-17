package com.example.bookstorerest.repository;

import com.example.bookstorerest.entity.Book;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByNameBook(String nameBook);

    Optional<Book> findByNameAuthor(String nameAuthor);

    Optional<Book> findByGenre(String genre);

    Book findByNameBookAndNameAuthor(String nameBook, String nameAuthor);

    List<Book> findAllByGenre(String genre, Sort sort);
}
