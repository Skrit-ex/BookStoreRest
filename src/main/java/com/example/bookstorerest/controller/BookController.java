package com.example.bookstorerest.controller;


import com.example.bookstorerest.entity.Book;
import com.example.bookstorerest.entity.BookFullDescription;
import com.example.bookstorerest.repository.BookRepository;
import com.example.bookstorerest.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @GetMapping("/updateBookLibrary")
    public ResponseEntity<String> updateLibrary() {
        bookService.updateLibrary();
        return ResponseEntity.ok("Library was update");
    }

    @PostMapping("/deleteByNameBook/{nameBook}")
    public ResponseEntity<String> delete(@PathVariable String nameBook) {
        Optional<Book> optionalBook = bookService.deleteByNameBook(nameBook);
        if (optionalBook.isPresent()) {
            return ResponseEntity.ok("Book with nameBook '" + nameBook + "' was delete");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with nameBook " + nameBook + "' not found");
        }
    }

    @DeleteMapping("/deleteAllBooks")
    public ResponseEntity<String> deleteAllBooks() {
        bookRepository.deleteAll();
        return ResponseEntity.ok("BookLibrary is empty");
    }

    @GetMapping("/descriptionByNameBook/{nameBook}")
    public ResponseEntity<String> descriptionByName(@PathVariable String nameBook) {
        Optional<BookFullDescription> descriptionBook = bookService.getAllDescription(nameBook);
        if (descriptionBook.isPresent()) {
            BookFullDescription description1 = descriptionBook.get();
            return ResponseEntity.ok(description1.getBookName() + ": Full description -> " + description1.getFullDescription());
        }
        return ResponseEntity.status(404).body("Book not found or other errors");
    }
}