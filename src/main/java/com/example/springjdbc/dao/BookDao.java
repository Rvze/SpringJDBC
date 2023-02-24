package com.example.springjdbc.dao;

import com.example.springjdbc.model.Book;
import com.example.springjdbc.model.Genre;

import java.util.List;

public interface BookDao {
    int getAgeLimit(String bookName);

    Book save(Book book, Long authorId, Long libraryid, Genre genre);

    void checkIsExsitBook(String bookname);

    List<Book> getBookById(Long bookId);

    void deleteBookById(Long bookId);

    List<Book> getBooksByAuthorName(String authorName);

}
