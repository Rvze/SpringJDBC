package com.example.springjdbc.dao.impl;

import com.example.springjdbc.dao.LibraryDao;
import com.example.springjdbc.model.Library;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class LibraryDaoJdbc implements LibraryDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public int countOfBooks(String libraryName) {
        String sql = """
                select count(*)
                from books_libraries
                         join libraries l on l.libraryid = books_libraries.libraryid
                where libraryname = :libraryname""";
        Map<String, String> param = Map.of("libraryname", libraryName);
        return jdbcTemplate.queryForObject(sql, param, Integer.class);
    }

    @Override
    public Long getOrSaveLibrary(String libraryname) {
        String findSql = "select * from libraries  where libraryname=:libraryname";
        Map<String, String> param = Map.of("libraryname", libraryname);
        var library = jdbcTemplate.query(findSql, param, new LibraryMapper());
        if (library.isEmpty()) {
            String createSql = "insert into libraries(libraryname) values(:libraryname) returning libraryid";
            Map<String, String> createParam = Map.of("libraryname", libraryname);
            return jdbcTemplate.queryForObject(createSql, createParam, Long.class);
        } else
            return library.get(0).getId();
    }

    @Override
    public Set<Library> getLibrariesByBookName(String bookName) {
        String sql = """
                select libraries.libraryid, libraryname
                from libraries
                         join books_libraries bl on libraries.libraryid = bl.libraryid
                         join books b on b.bookid = bl.bookid
                where bookname =:bookname""";
        Map<String, String> param = Map.of("bookname", bookName);
        return new HashSet<>(jdbcTemplate.query(sql, param, new LibraryMapper()));
    }

    public static class LibraryMapper implements RowMapper<Library> {

        @Override
        public Library mapRow(ResultSet rs, int rowNum) throws SQLException {
            var id = rs.getLong("libraryid");
            var libraryName = rs.getString("libraryname");
            return Library.builder().id(id).libraryName(libraryName).build();
        }
    }
}
