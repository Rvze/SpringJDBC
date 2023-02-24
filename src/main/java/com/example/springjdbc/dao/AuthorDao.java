package com.example.springjdbc.dao;

import com.example.springjdbc.model.Author;

import java.util.List;

public interface AuthorDao {

    Author save(Author author);

    int count();

    int countOfBooks(String authorName);

    int getRating(String authorName);

    String getBiography(String authorName);

    Author getAuthor(String authorName);

    List<Author> getAuthorsByBookName(String bookName);


}
