package com.example.springjdbc.dao;

public interface LibraryDao {

    int countOfBooks(String libraryName);

    Long getOrSaveLibrary(String libraryname);
}
