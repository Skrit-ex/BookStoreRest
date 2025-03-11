package com.example.bookstorerest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameBook;
    private String nameAuthor;
    private String description;
    private String genre;

    public Book(String nameBook, String nameAuthor, String genre, String description) {
        this.nameBook = nameBook;
        this.nameAuthor = nameAuthor;
        this.description = description;
        this.genre = genre;
    }
}
