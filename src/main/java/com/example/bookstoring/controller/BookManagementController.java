package com.example.bookstoring.controller;

import com.example.bookstoring.model.BookRequest;
import com.example.bookstoring.repository.BookRepository;
import com.example.bookstoring.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("author/api/v1/books")
public class BookManagementController {

    private final BookService bookService;
    private final BookRepository bookRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('author:create')")
    public void createBook(@RequestBody BookRequest bookRequest){
        System.out.println(bookRequest.toString());
        bookService.createBook(bookRequest);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('author:delete')")
    public ResponseEntity<Object> deleteBook(@PathVariable Long id){
        return bookRepository.findById(id)
                .map(book -> {
                    bookRepository.delete(book);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
