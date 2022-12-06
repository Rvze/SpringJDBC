package com.example.springjdbc.dao.impl;

import com.example.springjdbc.exception.ValidationException;
import com.example.springjdbc.model.Book;
import com.example.springjdbc.model.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@DisplayName(value = "Dao для работы с автороами")
@ExtendWith(SpringExtension.class)
@Import({BookDaoJdbc.class, GenreDaoJdbc.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookDaoJdbcTest {

    private static final String BOOK_NAME = "Евгений Онегин";
    private static final String BOOK_GENRE = "Roman";
    private static final int AGE_LIMIT = 16;
    private static final String BOOK_AUTHOR = "Пушкин";
    private static final String BOOK_LIBRARY = "РНБ";
    private static final Long BOOK_ID = 1L;

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Test
    @DisplayName("Получить жанр книги")
    void getGenre() {
        var genreFromDb = bookDaoJdbc.getGenre(BOOK_NAME);
        assertEquals(BOOK_GENRE, genreFromDb);
    }

    @Test
    @DisplayName("Получить возростное ограничение книги")
    void getAgeLimit() {
        var ageLimit = bookDaoJdbc.getAgeLimit(BOOK_NAME);
        assertEquals(AGE_LIMIT, ageLimit);
    }

    @Test
    @DisplayName("Получить авторов этой книги")
    void getAuthors() {
        var authors = bookDaoJdbc.getAuthors(BOOK_NAME);
        assertEquals(BOOK_AUTHOR, authors.get(0));
    }

    @Test
    @DisplayName("Получить библиотеки, где хранится книга")
    void getLibraries() {
        var libraries = bookDaoJdbc.getLibraries(BOOK_NAME);
        List<String> list = new ArrayList<>(libraries);
        assertEquals(BOOK_LIBRARY, list.get(0));
    }

    @Test
    @DisplayName("Сохранить книгу")
    void save() {
        Book book = Book.builder().bookName("book")
                .title("title").year(2022).genreId(1L).build();
        Genre genre = Genre.builder().id(1L).build();
        Long authorId = 1L;
        Long libraryId = 1L;
        var result = bookDaoJdbc.save(book, authorId, libraryId, genre);
        assertThrows(ValidationException.class, () -> bookDaoJdbc.checkIsExsitBook(result.getBookName()));
    }

    @Test
    @DisplayName("Проверить существует ли книга, если существует выбросится исключение")
    void checkIsExsitBook() {
        assertThrows(ValidationException.class, () -> bookDaoJdbc.checkIsExsitBook(BOOK_NAME));
    }

    @Test
    @DisplayName("Получить книгу по id")
    void getBookById() {
        var result = bookDaoJdbc.getBookById(BOOK_ID);
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("Удалить книгу по id")
    void deleteBookById() {
        bookDaoJdbc.deleteBookById(BOOK_ID);
        var books = bookDaoJdbc.getBookById(BOOK_ID);
        assertTrue(books.isEmpty());
    }

}