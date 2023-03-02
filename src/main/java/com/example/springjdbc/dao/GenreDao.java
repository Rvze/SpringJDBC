package com.example.springjdbc.dao;

import com.example.springjdbc.model.Genre;

public interface GenreDao {
    Genre save(Genre genre);

    Long checkIsExist(String genreName);

    Genre findById(Long genreId);

    Genre getGenreByBookName(String bookName);

}
