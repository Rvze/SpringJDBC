package com.example.springjdbc.service;

import com.example.springjdbc.model.Author;
import com.example.springjdbc.model.Book;

import java.util.List;

public interface AuthorService {
    Author save(Author author);

    int count();

    int countOfBooks(String authorName);

    int getRating(String authorName);

    String getBiography(String authorName);

    Author getAuthor(String authorName);

    List<Author> getAuthorsByBookName(String bookName);
}
