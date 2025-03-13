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

    @Column(name = "name_book")
    private String nameBook;
    @Column(name = "name_author")
    private String nameAuthor;
    @Column(name = "genre")
    private String genre;
    @Column(name = "brief_description")
    private String description;


    public Book(String nameBook, String nameAuthor, String genre, String description) {
        this.nameBook = nameBook;
        this.nameAuthor = nameAuthor;
        this.genre = genre;
        this.description = description;
    }
}
