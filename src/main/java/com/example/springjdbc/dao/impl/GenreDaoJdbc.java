package com.example.springjdbc.dao.impl;

import com.example.springjdbc.dao.GenreDao;
import com.example.springjdbc.model.Genre;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@RequiredArgsConstructor
@Repository
@Slf4j
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbcTemplate;

    @Override
    public Genre save(Genre genre) {
        String saveGenreSql = "insert into genres(genrename, description, agelimit) values(:genrename, :description, :agelimit)";
        jdbcTemplate.update(saveGenreSql, Map.of("genrename", genre.getGenreName(),
                "description", genre.getDescription(),
                "agelimit", genre.getAgeLimit()));
        String findIdSql = "select * from genres where genrename=:genrename";
        var result = jdbcTemplate.query(findIdSql, Map.of("genrename", genre.getGenreName()), new GenreMapper());
        return result.get(0);
    }

    @Override
    public Long checkIsExist(String genreName) {
        String sql = "select * from genres g where genrename =:genreName";
        Map<String, String> params = Map.of("genreName", genreName);
        var genre = jdbcTemplate.query(sql, params, new GenreMapper());
        if (genre.isEmpty()) {
            return null;
        }
        return genre.get(0).getId();

    }

    @Override
    public Genre findById(Long genreId) {
        String sql = "select * from genres where genreid =:genreId";
        Map<String, Long> param = Map.of("genreId", genreId);
        return jdbcTemplate.query(sql, param, new GenreMapper()).get(0);
    }

    @Override
    public Genre getGenreByBookName(String bookName) {
        try {
            String sql = """
                    select *
                    from genres
                             left join books b on b.genreid = genres.genreid
                    where bookname=:bookname""";
            Map<String, String> params = Map.of("bookname", bookName);
            return jdbcTemplate.query(sql, params, new GenreMapper()).get(0);
        } catch (DataAccessException e) {
            log.error("Такой книги нет! {}", bookName);
        }
        return null;
    }

    public static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Genre.builder().id(rs.getLong("genreid"))
                    .genreName(rs.getString("genrename"))
                    .description(rs.getString("description"))
                    .ageLimit(rs.getInt("agelimit"))
                    .build();
        }
    }
}
