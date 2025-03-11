package com.example.bookstorerest.controller;


import com.example.bookstorerest.repository.BookRepository;
import com.example.bookstorerest.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/deleteByNameBook/{nameBook}")
    public ResponseEntity<String> delete(@PathVariable String nameBook){
        bookService.deleteByNameBook(nameBook);
        return ResponseEntity.ok("Book with nameBook '" + nameBook + "' was delete");
    }
    @DeleteMapping("/deleteAllBooks")
    public ResponseEntity<String> deleteAllBooks(){
        bookRepository.deleteAll();
        return ResponseEntity.ok("BookLibrary is empty");
    }
}
