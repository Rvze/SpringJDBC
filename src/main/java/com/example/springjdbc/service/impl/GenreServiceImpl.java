package com.example.springjdbc.service.impl;

import com.example.springjdbc.dao.GenreDao;
import com.example.springjdbc.model.Genre;
import com.example.springjdbc.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Override
    public Genre save(Genre genre) {
        return genreDao.save(genre);
    }

    @Override
    public Genre checkIsExist(Genre genre) {
        Long genreId = genreDao.checkIsExist(genre.getGenreName());
        return genreId == null ? save(genre) : genre;
    }

    @Override
    public Genre findById(Long genreId) {
        return genreDao.findById(genreId);
    }

    @Override
    public Genre getGenreByBookName(String bookName) {
        return genreDao.getGenreByBookName(bookName);
    }
}
