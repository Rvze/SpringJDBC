package com.example.springjdbc.dao.impl;

import com.example.springjdbc.model.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@DisplayName(value = "Dao для работы с автороами")
@ExtendWith(SpringExtension.class)
@Import(GenreDaoJdbc.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GenreDaoJdbcTest {

    private static final String GENRE_NAME = "Roman";
    private static final Long GENRE_ID = 1L;

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @Test
    @DisplayName("Сохранить жанр")
    void save() {
        Genre genre = Genre.builder().genreName("horror").description("страшный жанр")
                .ageLimit(18).build();
        var result = genreDaoJdbc.save(genre);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Проверить, существует ли этот жанр")
    void checkIsExist() {
        var result = genreDaoJdbc.checkIsExist(GENRE_NAME);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Проверить, существует ли этот жанр")
    void checkIsExistThrows() {
        var result = genreDaoJdbc.checkIsExist("anything");
        assertNull(result);
    }

    @Test
    @DisplayName("Найти жанр по id")
    void findById() {
        var genre = genreDaoJdbc.findById(GENRE_ID);
        assertNotNull(genre);
    }

    @Test
    @DisplayName("Получить жанр по имени книги")
    void getGenreByBookName() {
        var bookname = "Евгений Онегин";
        assertDoesNotThrow(() -> {
            var result = genreDaoJdbc.getGenreByBookName(bookname);
            assertEquals(GENRE_NAME, result.getGenreName());
        });
    }

}