package com.example.springjdbc.dao.impl;

import com.example.springjdbc.dao.AuthorDao;
import com.example.springjdbc.exception.ValidationException;
import com.example.springjdbc.model.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbcTemplate;

    @Override
    public Author save(Author author) {
        String findSql = "select * from authors";
        var authors = jdbcTemplate.query(findSql, new AuthorMapper());
        authors.forEach(author1 -> {
            if (author1.getAuthorName().equals(author.getAuthorName())) {
                throw new ValidationException("Такой автор уже существует, проверьте свои ввод!");
            }
        });
        String insertSql = "insert into authors (authorname, rating,yearofbirth,biography) values(:authorname, :rating," +
                ":yearofbirth, :biography)";
        var result = jdbcTemplate.update(insertSql, Map.of("authorname", author.getAuthorName(),
                "rating", author.getRating(),
                "yearofbirth", author.getYearOfBirth(),
                "biography", author.getBiography()));
        return result == 1 ? author : null;
    }

    @Override
    public int count() {
        return jdbcTemplate
                .queryForObject("select count(*) from authors", Map.of(), Integer.class);
    }

    @Override
    public int countOfBooks(String authorName) {
        String sql = """
                select count(ba.bookid)
                from authors
                         left join books_authors ba on authors.authorid = ba.authorid
                         left join books_libraries bl on ba.bookid = bl.bookid
                where authorname=:authorname""";
        Map<String, String> map = Map.of("authorname", authorName);
        return jdbcTemplate.queryForObject(sql, map, Integer.class);
    }

    @Override
    public int getRating(String authorName) {
        String sql = "select rating from authors where authorname=:authorname";
        Map<String, String> param = Map.of("authorname", authorName);
        return jdbcTemplate.queryForObject(sql, param, Integer.class);
    }

    @Override
    public String getBiography(String authorName) {
        String sql = "select biography from authors where authorname=:authorname";
        Map<String, String> param = Map.of("authorname", authorName);
        return jdbcTemplate.queryForObject(sql, param, String.class);
    }

    @Override
    public Author getAuthor(String authorName) {
        String findSql = "select * from authors a where authorname=:authorName";
        Map<String, String> params = Map.of("authorName", authorName);
        try {
            return jdbcTemplate.query(findSql, params, new AuthorMapper()).get(0);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Author> getAuthorsByBookName(String bookName) {
        try {
            String sql = """
                    select *
                    from authors
                             right join books_authors ba on authors.authorid = ba.authorid
                             right join books b on ba.bookid = b.bookid
                    where bookname =:bookname""";
            Map<String, String> param = Map.of("bookname", bookName);
            return jdbcTemplate.query(sql, param, new AuthorMapper());
        } catch (DataAccessException e) {
            throw new ValidationException("Такой книги нет!");
        }
    }

    public static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            var id = rs.getLong("authorid");
            var authorName = rs.getString("authorname");
            var rating = rs.getInt("rating");
            var yearOfBirth = rs.getInt("yearofbirth");
            var biography = rs.getString("biography");
            return Author.builder().id(id).authorName(authorName).rating(rating).yearOfBirth(yearOfBirth)
                    .biography(biography).build();
        }
    }
}
