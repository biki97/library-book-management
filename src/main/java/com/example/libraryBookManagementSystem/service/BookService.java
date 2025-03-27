package com.example.libraryBookManagementSystem.service;

import com.example.libraryBookManagementSystem.entity.Book;
import com.example.libraryBookManagementSystem.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Add a new book
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    // Get all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Get book by ID
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // Get book by title
    public Optional<Book> getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    // Update book details
    public Book updateBook(Long id, Book updatedBook) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                    book.setGenre(updatedBook.getGenre());
                    book.setAvailable(updatedBook.isAvailable());
                    return bookRepository.save(book);
                }).orElseThrow(() -> new RuntimeException("Book not found with ID: " + id));
    }


    // Delete book by ID
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false; // Returns false if book does not exist
    }
}
