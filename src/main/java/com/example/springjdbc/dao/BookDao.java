package com.example.springjdbc.dao;

import com.example.springjdbc.model.Author;
import com.example.springjdbc.model.Book;
import com.example.springjdbc.model.Genre;
import com.example.springjdbc.model.Library;

import java.util.List;
import java.util.Set;

public interface BookDao {
    String getGenre(String bookName);

    int getAgeLimit(String bookName);

    List<String> getAuthors(String bookName);

    Set<String> getLibraries(String bookName);

    Book save(Book book, Long authorId, Long libraryid, Genre genre);

    void checkIsExsitBook(String bookname);

    List<Book> getBookById(Long bookId);

    void deleteBookById(Long bookId);
}
