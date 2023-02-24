package com.example.springjdbc.service.impl;

import com.example.springjdbc.dao.LibraryDao;
import com.example.springjdbc.model.Library;
import com.example.springjdbc.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final LibraryDao libraryDao;

    @Override
    public int countOfBooks(String libraryName) {
        return libraryDao.countOfBooks(libraryName);
    }

    @Override
    public Long getOrSaveLibrary(String libraryname) {
        return libraryDao.getOrSaveLibrary(libraryname);
    }

    @Override
    public Set<Library> getLibrariesByBookName(String bookName) {
        return libraryDao.getLibrariesByBookName(bookName);
    }
}
