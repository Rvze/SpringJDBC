package com.example.springjdbc.dao;

import com.example.springjdbc.model.Library;

import java.util.Set;

public interface LibraryDao {

    int countOfBooks(String libraryName);

    Long getOrSaveLibrary(String libraryname);

    Set<Library> getLibrariesByBookName(String bookName);

}
