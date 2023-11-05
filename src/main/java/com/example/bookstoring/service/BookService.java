package com.example.bookstoring.service;

import com.example.bookstoring.entity.Book;
import com.example.bookstoring.mapper.BookMapper;
import com.example.bookstoring.model.BookRequest;
import com.example.bookstoring.model.BookResponse;
import com.example.bookstoring.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static com.example.bookstoring.mapper.BookMapper.mapEntityToResponse;


@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository bookRepository;


    public void createBook(BookRequest bookRequest){
        bookRepository.save(BookMapper.mapToBookEntity(bookRequest));
    }
    public BookResponse getBookById(Long id){
        log.info("ActionLog.getBook.start id:{}", id);
        var book = fetchBookIfExist(id);

        log.info("Actionlog.getBook.end id:{}", id);
        return mapEntityToResponse(book);
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public boolean deleteBook(Long id){
        Book existingBook = bookRepository.findById(id).orElse(null);
        if (existingBook != null){
            bookRepository.delete(existingBook);
            return true;
        }
        return false;
    }

    private Book fetchBookIfExist(Long id){
        return bookRepository.findById(id)
                .orElseThrow(() ->{
                    log.error("ActionLog.getBook.error id:{}", id);
                    throw new RuntimeException("Book Not Found");
                });
    }
}
