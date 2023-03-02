package com.example.springjdbc.service.impl;

import com.example.springjdbc.dao.BookDao;
import com.example.springjdbc.model.Book;
import com.example.springjdbc.model.Genre;
import com.example.springjdbc.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    @Override
    public Book save(Book book, Long authorId, Long libraryId, Genre genre) {
        return bookDao.save(book, authorId, libraryId, genre);
    }

    @Override
    public int getBookAgeLimit(String bookName) {
        return bookDao.getAgeLimit(bookName);
    }

    @Override
    public void checkIsExistBook(String bookName) {
        bookDao.checkIsExsitBook(bookName);
    }

    @Override
    public Optional<Book> getBookById(Long bookId) {
        return bookDao.getBookById(bookId);
    }

    @Override
    public void deleteBookById(Long bookId) {
        bookDao.deleteBookById(bookId);
    }

    @Override
    public List<Book> getBooksByAuthorName(String authorName) {
        return bookDao.getBooksByAuthorName(authorName);
    }
}
