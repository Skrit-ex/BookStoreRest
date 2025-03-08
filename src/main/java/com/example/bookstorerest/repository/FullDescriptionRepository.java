package com.example.bookstorerest.repository;

import com.example.bookstorerest.entity.BookFullDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FullDescriptionRepository extends JpaRepository<BookFullDescription, Long> {
}
