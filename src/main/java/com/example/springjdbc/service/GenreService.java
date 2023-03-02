package com.example.springjdbc.service;

import com.example.springjdbc.model.Genre;

public interface GenreService {
    Genre save(Genre genre);

    Genre checkIsExist(Genre genre);

    Genre findById(Long genreId);

    Genre getGenreByBookName(String bookName);
}
