package com.example.springjdbc.service;

import com.example.springjdbc.model.Book;
import com.example.springjdbc.model.Genre;

import java.util.List;

public interface BookService {
    Book save(Book book, Long authorId, Long libraryId, Genre genre);

    int getBookAgeLimit(String bookName);

    void checkIsExistBook(String bookName);

    List<Book> getBookById(Long bookId);

    void deleteBookById(Long bookId);

    List<Book> getBooksByAuthorName(String authorName);


}
