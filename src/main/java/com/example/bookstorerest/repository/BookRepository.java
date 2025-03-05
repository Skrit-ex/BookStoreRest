package com.example.bookstorerest.repository;

import com.example.bookstorerest.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
