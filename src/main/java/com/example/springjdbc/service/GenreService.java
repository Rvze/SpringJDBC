package com.example.springjdbc.service;

import com.example.springjdbc.model.Genre;

public interface GenreService {
    Genre save(Genre genre);

    Long checkIsExist(String genreName);

    Genre findById(Long genreId);

    Genre getGenreByBookName(String bookName);
}
