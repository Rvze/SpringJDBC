package com.example.springjdbc.dao;

import com.example.springjdbc.model.Book;
import com.example.springjdbc.model.Genre;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    int getAgeLimit(String bookName);

    Book save(Book book, Long authorId, Long libraryid, Genre genre);

    void checkIsExsitBook(String bookname);

    Optional<Book> getBookById(Long bookId);

    void deleteBookById(Long bookId);

    List<Book> getBooksByAuthorName(String authorName);

}
