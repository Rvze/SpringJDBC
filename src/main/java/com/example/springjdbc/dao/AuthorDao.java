package com.example.springjdbc.dao;

import com.example.springjdbc.model.Author;
import com.example.springjdbc.model.Book;

import java.util.List;

public interface AuthorDao {

    Author save(Author author);

    int count();

    int countOfBooks(String authorName);

    int getRating(String authorName);

    String getBiography(String authorName);

    List<String> getBooks(String authorName);

    Long getAuthor(String authorName);

}
