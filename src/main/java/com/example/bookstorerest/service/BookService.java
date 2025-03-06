package com.example.bookstorerest.service;

import com.example.bookstorerest.entity.Book;
import com.example.bookstorerest.entity.User;
import com.example.bookstorerest.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

@Slf4j
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;


    public Optional<Book> findById(Long id) {
        Optional<Book> bookId = bookRepository.findById(id);
        if (bookId.isPresent()) {
            log.info("User with id '" + id + ("' was found"));
            return bookId;
        } else {
            log.warn("User with id '" + id + "' not found");
            return Optional.empty();
        }
    }

    public Optional<Book> findByNameBook(String nameBook){
        Optional<Book> optionalBook = bookRepository.findByNameBook(nameBook);
        if(optionalBook.isPresent()){
            log.info("Book with bookName '" + nameBook + "' was found");
            return optionalBook;
        }else {
            log.error("Book with bookName '" + nameBook + "' wasn't found");
            return Optional.empty();
        }
    }

    public Optional<Book> findByNameAuthor(String nameAuthor){
        Optional<Book> optionalBook = bookRepository.findByNameAuthor(nameAuthor);
        if(optionalBook.isPresent()){
            log.info("Book with nameAuthor '" + nameAuthor + "' was found");
            return optionalBook;
        }else {
            log.error("Book with nameAuthor '" + nameAuthor + "' wasn't found");
            return Optional.empty();
        }
    }
    public Optional<Book> findByGenre(String genre){
        Optional<Book> optionalBook = bookRepository.findByGenre(genre);
        if(optionalBook.isPresent()){
            log.info("Book with genre '" + genre + "' wasn't found");
            return optionalBook;
        }else {
            log.error("Book with genre '" + genre + "' wasn't found");
            return Optional.empty();
        }
    }
    public BufferedReader createBufferReader(InputStreamReader inputStreamReader){
        return new BufferedReader(inputStreamReader);}

    public void readAndSaveDescription(){
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("bookDescription.txt");
        if(inputStream == null){
            log.error("file not found");
            return;
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        try (BufferedReader bufferedReader = createBufferReader(inputStreamReader)){
        }
    }
}
