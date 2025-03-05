package com.example.bookstorerest.service;

import com.example.bookstorerest.entity.Book;
import com.example.bookstorerest.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;


    public Optional<Book> findById (Long id) {
        Optional<Book> bookId = bookRepository.findById(id);
            if (bookId.isPresent()) {
                log.info("User with id '" + id + ("' was found"));
                return bookId;
            } else {
                log.warn("User with id '" + id + "' not found");
                return Optional.empty();
        }
    }
}
