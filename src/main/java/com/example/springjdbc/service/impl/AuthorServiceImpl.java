package com.example.springjdbc.service.impl;

import com.example.springjdbc.dao.AuthorDao;
import com.example.springjdbc.model.Author;
import com.example.springjdbc.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    @Override
    public Author save(Author author) {
        return authorDao.save(author);
    }

    @Override
    public int count() {
        return authorDao.count();
    }

    @Override
    public int countOfBooks(String authorName) {
        return authorDao.countOfBooks(authorName);
    }

    @Override
    public int getRating(String authorName) {
        return authorDao.getRating(authorName);
    }

    @Override
    public String getBiography(String authorName) {
        return authorDao.getBiography(authorName);
    }

    @Override
    public Author getAuthor(String authorName) {
        return authorDao.getAuthor(authorName);
    }

    @Override
    public List<Author> getAuthorsByBookName(String bookName) {
        return authorDao.getAuthorsByBookName(bookName);
    }
}
