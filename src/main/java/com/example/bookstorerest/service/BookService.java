package com.example.bookstorerest.service;

import com.example.bookstorerest.entity.Book;
import com.example.bookstorerest.entity.BookFullDescription;
import com.example.bookstorerest.repository.BookRepository;
import com.example.bookstorerest.repository.FullDescriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private FullDescriptionRepository descriptionRepository;


    public Optional<Book> findById(Long id) {
        Optional<Book> bookId = bookRepository.findById(id);
        if (bookId.isPresent()) {
            log.info("User with id {} was found", id);
            return bookId;
        } else {
            log.warn("User with id {} not found", id);
            return Optional.empty();
        }
    }

    public Optional<Book> findByNameBook(String nameBook) {
        Optional<Book> optionalBook = bookRepository.findByNameBook(nameBook);
        if (optionalBook.isPresent()) {
            log.info("Book with bookName {} was found", nameBook);
            return optionalBook;
        } else {
            log.error("Book with bookName {} wasn't found", nameBook);
            return Optional.empty();
        }
    }

    public Optional<Book> findByNameAuthor(String nameAuthor) {
        Optional<Book> optionalBook = bookRepository.findByNameAuthor(nameAuthor);
        if (optionalBook.isPresent()) {
            log.info("Book with nameAuthor {} was found", nameAuthor);
            return optionalBook;
        } else {
            log.error("Book with nameAuthor {} wasn't found", nameAuthor);
            return Optional.empty();
        }
    }

    public Optional<Book> findByNameAuthor1(String nameAuthor){
        return bookRepository.findByNameAuthor(nameAuthor).map(book -> {
            log.info("Book with nameAuthor {} was found", nameAuthor);
            return Optional.of(book);
        }).orElseGet(()->{
            log.error("Book with nameAuthor {} wasn't found", nameAuthor);
            return Optional.empty();
        });
    }

    public Optional<Book> findByGenre(String genre) {
        Optional<Book> optionalBook = bookRepository.findByGenre(genre);
        if (optionalBook.isPresent()) {
            log.info("Book with genre {} wasn't found", genre);
            return optionalBook;
        } else {
            log.error("Book with genre {} wasn't found", genre);
            return Optional.empty();
        }
    }

    @Transactional
    public void updateLibrary(){
        readAndSaveData("books.txt", this::parseBooks);
        readAndSaveData("bookDescription.txt", this::parseFullDescription);
    }

    public void readAndSaveData(String fileName, Consumer<String[]> dataProcessor) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null){
            log.error("File not found with fileName -> {}", fileName);
            return;
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)){
            String line;
            int lineNumber = 0;
            while ((line = bufferedReader.readLine()) != null){
                lineNumber++;
                if(lineNumber <=2) continue;
                if(!line.isEmpty()){
                    String[] data = line.split("\\|");
                    dataProcessor.accept(data);
                }
            }
        }catch (IOException e){
            log.error("Error reading fileName -> {}", fileName);
        }

    }
    private void parseBooks(String[] data) {
        String nameBook = data[0].trim();
        String nameAuthor = data[1].trim();
        String genre = data[2].trim();
        String description = data[3].trim();
        Book existBook = bookRepository.findByNameBookAndNameAuthor(nameBook, nameAuthor);
        if (existBook != null) {
            log.error("This book exist and was update");
            existBook.setGenre(genre);
            existBook.setDescription(description);
            bookRepository.save(existBook);
        } else {
            Book book = new Book(nameBook, nameAuthor, genre, description);
            bookRepository.save(book);
        }
    }
    private void parseFullDescription(String[] data) {
        if(data.length <2){
            log.error("Invalid data format : {}", Arrays.toString(data));
            return;
        }
        String nameBook = data[0];
        String fullDescription = data[1];
        descriptionRepository.findByBookName(nameBook).ifPresentOrElse(bookDescription ->{
            bookDescription.setFullDescription(fullDescription);
            log.warn("BookDescription for book :{}  was update", nameBook);
            descriptionRepository.save(bookDescription);
        },
                () ->{
            BookFullDescription bookFullDescription = new BookFullDescription(nameBook, fullDescription);
            Optional<Book> currentBook = bookRepository.findByNameBook(nameBook);
            bookFullDescription.setBook(currentBook.get());
            log.info("Book and description was saved");
            descriptionRepository.save(bookFullDescription);
                });
//        Optional<BookFullDescription> bookFullDescription = descriptionRepository.findByBookName(nameBook);
//        if (bookFullDescription.isPresent()) {
//            bookFullDescription.get().setFullDescription(fullDescription);
//            descriptionRepository.save(bookFullDescription.get());
//        } else {
//            BookFullDescription description = new BookFullDescription(nameBook, fullDescription);
//            descriptionRepository.save(description);
//        }
    }

    public Optional<Book> deleteByNameBook(String nameBook) {
        return bookRepository.findByNameBook(nameBook).map(book -> {
            log.info("Book with nameBook {} was found and deleted",nameBook);
            bookRepository.delete(book);
            return Optional.of(book);
        }).orElseGet(() -> {
            log.error("Book with nameBook {} was not found",nameBook);
            return Optional.empty();
        });
    }
    public Optional<BookFullDescription> getAllDescription(String nameBook){
        return descriptionRepository.findByBookName(nameBook).map(descriptionBook -> {
            log.info("BookDescription with bookName {} was found",nameBook);
            return Optional.of(descriptionBook);
        }).orElseGet(() -> {
            log.error("BookDescription with bookName {} was not found",nameBook);
            return Optional.empty();
        });
    }
    public Optional<BookFullDescription> findDescriptionByNameBook(String nameBook){
        return descriptionRepository.findByBookName(nameBook).map(book -> {
            log.error("Book {} was found", nameBook);
            return Optional.of(book);
        }).orElseGet(() -> {
            log.error(" Book {} wasn't found ", nameBook);
            return Optional.empty();
        });
    }
}
