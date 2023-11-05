package com.example.bookstoring.controller;

import com.example.bookstoring.entity.Book;
import com.example.bookstoring.model.BookResponse;
import com.example.bookstoring.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public List<Book> getAllBooks(){

        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public BookResponse getBookById(@PathVariable Long id){

        return bookService.getBookById(id);
    }


}
