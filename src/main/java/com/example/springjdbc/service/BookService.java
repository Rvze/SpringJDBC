package com.example.springjdbc.service;

import com.example.springjdbc.model.Book;
import com.example.springjdbc.model.Genre;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book save(Book book, Long authorId, Long libraryId, Genre genre);

    int getBookAgeLimit(String bookName);

    void checkIsExistBook(String bookName);

    Optional<Book> getBookById(Long bookId);

    void deleteBookById(Long bookId);

    List<Book> getBooksByAuthorName(String authorName);


}
