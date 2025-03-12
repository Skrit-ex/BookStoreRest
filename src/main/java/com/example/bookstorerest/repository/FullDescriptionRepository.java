package com.example.bookstorerest.repository;

import com.example.bookstorerest.entity.Book;
import com.example.bookstorerest.entity.BookFullDescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FullDescriptionRepository extends JpaRepository<BookFullDescription, Long> {

    Optional<BookFullDescription> findByBookName(String nameBook);
}
