package com.example.bookstorerest.controller;


import com.example.bookstorerest.repository.BookRepository;
import com.example.bookstorerest.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @GetMapping("/updateBookLibrary")
    public ResponseEntity<String> updateLibrary(){
        bookService.readAndSaveBookLibrary();
        return ResponseEntity.ok("Library was update");
    }
}
