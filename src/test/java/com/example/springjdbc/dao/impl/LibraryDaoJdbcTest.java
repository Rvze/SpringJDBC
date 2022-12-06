package com.example.springjdbc.dao.impl;

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
@Import(LibraryDaoJdbc.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LibraryDaoJdbcTest {

    private static final String LIBRARY_NAME = "РНБ";
    private static final int COUNT_OF_BOOKS = 1;
    private static final Long LIBRARY_ID = 1L;

    @Autowired
    private LibraryDaoJdbc libraryDaoJdbc;

    @Test
    @DisplayName("Получить кол-во книг в библиотеке")
    void countOfBooks() {
        var count = libraryDaoJdbc.countOfBooks(LIBRARY_NAME);
        assertEquals(COUNT_OF_BOOKS, count);
    }

    @Test
    @DisplayName("Получить книгу по имени")
    void getLibrary() {
        var res = libraryDaoJdbc.getOrSaveLibrary(LIBRARY_NAME);
        assertEquals(LIBRARY_ID, res);
    }

    @Test
    @DisplayName("Сохранить книгу")
    void saveLibrary() {
        Long nextId = LIBRARY_ID;
        nextId++;
        var res = libraryDaoJdbc.getOrSaveLibrary("any");
        assertEquals(nextId, res);
    }

}