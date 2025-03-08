package com.example.bookstorerest.service;

import com.example.bookstorerest.entity.Book;
import com.example.bookstorerest.entity.BookFullDescription;
import com.example.bookstorerest.repository.BookRepository;
import com.example.bookstorerest.repository.FullDescriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;

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
            log.info("User with id '" + id + ("' was found"));
            return bookId;
        } else {
            log.warn("User with id '" + id + "' not found");
            return Optional.empty();
        }
    }

    public Optional<Book> findByNameBook(String nameBook) {
        Optional<Book> optionalBook = bookRepository.findByNameBook(nameBook);
        if (optionalBook.isPresent()) {
            log.info("Book with bookName '" + nameBook + "' was found");
            return optionalBook;
        } else {
            log.error("Book with bookName '" + nameBook + "' wasn't found");
            return Optional.empty();
        }
    }

    public Optional<Book> findByNameAuthor(String nameAuthor) {
        Optional<Book> optionalBook = bookRepository.findByNameAuthor(nameAuthor);
        if (optionalBook.isPresent()) {
            log.info("Book with nameAuthor '" + nameAuthor + "' was found");
            return optionalBook;
        } else {
            log.error("Book with nameAuthor '" + nameAuthor + "' wasn't found");
            return Optional.empty();
        }
    }

    public Optional<Book> findByGenre(String genre) {
        Optional<Book> optionalBook = bookRepository.findByGenre(genre);
        if (optionalBook.isPresent()) {
            log.info("Book with genre '" + genre + "' wasn't found");
            return optionalBook;
        } else {
            log.error("Book with genre '" + genre + "' wasn't found");
            return Optional.empty();
        }
    }

    public BufferedReader createBufferReader(InputStreamReader inputStreamReader) {
        return new BufferedReader(inputStreamReader);
    }

    public void readAndSaveDescription() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("bookDescription.txt");
        if (inputStream == null) {
            log.error("file not found");
            return;
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        try (BufferedReader bufferedReader = createBufferReader(inputStreamReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty()) {
                    String[] data = line.split("\\|");
                    String nameBook = data[0];
                    String description = data[1];
                    BookFullDescription bookFullDescription = new BookFullDescription(nameBook, description);
                    descriptionRepository.save(bookFullDescription);
                }
            }
        } catch (FileNotFoundException e) {
            log.error("File not found (method void readAndSaveDescription)");
        } catch (IOException e) {
            log.error(" Error reading file (method void readAndSaveDescription)");
        }
    }

    public void readAndSaveBookLibrary() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("book.txt");
        if (inputStream == null) {
            log.error("File not found or other error");
            return;
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        try (BufferedReader bufferedReader = createBufferReader(inputStreamReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty()) {
                    String[] data = line.split("\\|");
                    String nameBook = data[0];
                    String nameAuthor = data[1];
                    String genre = data[2];
                    String description = data[3];
                    Book booksLibrary = new Book(nameBook, nameAuthor, genre, description);
                    bookRepository.save(booksLibrary);
                }
            }
        } catch (FileNotFoundException e) {
            log.error("File not found");
        } catch (IOException e) {
            log.error("error reading file");
        }
    }
    public Optional<Book> deleteByNameBook(String nameBook){
        Optional<Book> optionalBook = bookRepository.findByNameBook(nameBook);
        if(optionalBook.isPresent()){
            log.info("Book with nameBook '" + nameBook + "' was found");
            return optionalBook;
        }else {
            log.error("Book with nameBook '" + nameBook + "' not found or other errors");
            return Optional.empty();
        }
    }
}
