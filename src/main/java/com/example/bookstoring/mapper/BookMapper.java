package com.example.bookstoring.mapper;


import com.example.bookstoring.entity.Book;
import com.example.bookstoring.model.BookRequest;
import com.example.bookstoring.model.BookResponse;

public class BookMapper {
    public static BookResponse mapEntityToResponse(Book book ){
        return BookResponse.builder()
                .id(book.getId())
                .name(book.getName())
                .build();
    }
    public static Book mapToBookEntity(BookRequest bookRequest){
        return Book.builder()
                .name(bookRequest.getName())
                .build();
    }
}
