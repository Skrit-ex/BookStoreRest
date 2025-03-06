package com.example.bookstorerest.repository;

import com.example.bookstorerest.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByNameBook(String nameBook);

    Optional<Book> findByNameAuthor(String nameAuthor);

    Optional<Book> findByGenre(String genre);
}
