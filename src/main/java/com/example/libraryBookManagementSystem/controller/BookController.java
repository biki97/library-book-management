package com.example.libraryBookManagementSystem.controller;

import com.example.libraryBookManagementSystem.entity.Book;
import com.example.libraryBookManagementSystem.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Home Page - Displays All Books
    @GetMapping("/")
    public String home(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "index"; // Loads index.html
    }

    // Show Add Book Form
    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book"; // Loads add-book.html
    }

    // Handle Adding a New Book
    @PostMapping("/add")
    public String addBook(@ModelAttribute("book") Book book) {
        bookService.addBook(book);
        return "redirect:/books/";
    }

    // View All Books
    @GetMapping("/list")
    public String listBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "book-list"; // Loads book-list.html
    }

    // Search Book by ID or Title
    @GetMapping("/search")
    public String searchBook(@RequestParam(required = false) Long id,
                             @RequestParam(required = false) String title,
                             Model model) {
        if (id != null) {
            Optional<Book> book = bookService.getBookById(id);
            book.ifPresent(value -> model.addAttribute("books", List.of(value)));
        } else if (title != null && !title.isEmpty()) {
            Optional<Book> book = bookService.getBookByTitle(title);
            book.ifPresent(value -> model.addAttribute("books", List.of(value)));
        } else {
            model.addAttribute("books", bookService.getAllBooks()); // Show all books if no input
        }
        return "book-list";
    }

//    // Show Update Book Form
//    @GetMapping("/update/{id}")
//    public String showUpdateForm(@PathVariable Long id, Model model) {
//        Optional<Book> book = bookService.getBookById(id);
//        if (book.isPresent()) {
//            model.addAttribute("book", book.get());
//            return "update-book"; // Loads update-book.html
//        }
//        return "redirect:/books/list"; // Redirects if book not found
//    }

    // Show Update Form
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            return "update-book"; // Ensure this template exists
        } else {
            return "redirect:/books/"; // Redirect if book not found
        }
    }


    // Handle Book Update
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute Book updatedBook) {
        bookService.updateBook(id, updatedBook);
        return "redirect:/books/list"; // Redirect to book list after update
    }


    // Delete Book by ID
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books/";
    }
}
