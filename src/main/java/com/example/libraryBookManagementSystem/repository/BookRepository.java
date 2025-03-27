package com.example.libraryBookManagementSystem.repository;

import com.example.libraryBookManagementSystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
}
