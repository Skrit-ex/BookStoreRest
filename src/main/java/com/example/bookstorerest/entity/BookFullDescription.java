package com.example.bookstorerest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fullDescription")
public class BookFullDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bookName;
    private String fullDescription;

    public BookFullDescription(String bookName, String fullDescription) {
        this.bookName = bookName;
        this.fullDescription = fullDescription;
    }
}
