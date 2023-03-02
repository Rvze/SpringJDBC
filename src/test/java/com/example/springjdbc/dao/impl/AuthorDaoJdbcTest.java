package com.example.springjdbc.dao.impl;

import com.example.springjdbc.model.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@DisplayName(value = "Dao для работы с автороами")
@Import(AuthorDaoJdbc.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorDaoJdbcTest {

    private final static int AUTHOR_COUNT = 2;
    private final static String PUSHKIN_NAME = "Пушкин";
    private final static int PUSHKIN_BOOKS = 1;
    private final static int PUSHKIN_RATING = 10;

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Test
    @DisplayName("Кол-во авторов в базе")
    void findCountOfAuthors() {
        int count = authorDaoJdbc.count();
        assertEquals(AUTHOR_COUNT, count);
    }

    @Test
    @DisplayName("Кол-во книг у автора")
    void findCountOfBooks() {
        int countOfBooks = authorDaoJdbc.countOfBooks(PUSHKIN_NAME);
        assertEquals(PUSHKIN_BOOKS, countOfBooks);
    }

    @Test
    @DisplayName("Найти рейтинг автора")
    void getRating() {
        int rating = authorDaoJdbc.getRating(PUSHKIN_NAME);
        assertEquals(PUSHKIN_RATING, rating);
    }

    @Test
    @DisplayName("Сохранить автора в базе")
    void save() {
        Author author = Author.builder().authorName("author").biography("cool author")
                .rating(5).yearOfBirth(2002).build();
        var savedAuthor = authorDaoJdbc.save(author);
        assertEquals(author, savedAuthor);
    }

    @Test
    @DisplayName("Получить книги по имени автора")
    void getAuthorsByBookName() {
        var bookName = "Евгений Онегин";
        assertDoesNotThrow(() -> {
            var result = authorDaoJdbc.getAuthorsByBookName(bookName);
            assertFalse(result.isEmpty());
            assertEquals(PUSHKIN_NAME, result.get(0).getAuthorName());
        });
    }


}