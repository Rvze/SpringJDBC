package com.example.springjdbc.service;

import com.example.springjdbc.model.Library;

import java.util.Set;

public interface LibraryService {
    int countOfBooks(String libraryName);

    Long getOrSaveLibrary(String libraryname);

    Set<Library> getLibrariesByBookName(String bookName);
}
