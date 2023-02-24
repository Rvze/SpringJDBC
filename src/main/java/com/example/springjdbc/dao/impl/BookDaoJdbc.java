package com.example.springjdbc.dao.impl;

import com.example.springjdbc.dao.BookDao;
import com.example.springjdbc.dao.GenreDao;
import com.example.springjdbc.exception.ValidationException;
import com.example.springjdbc.model.Author;
import com.example.springjdbc.model.Book;
import com.example.springjdbc.model.Genre;
import com.example.springjdbc.model.Library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public int getAgeLimit(String bookName) {
        try {
            String sql = "select agelimit from genres join books b on b.genreid=genres.genreid where bookname=:bookname";
            Map<String, String> param = Map.of("bookname", bookName);
            return jdbcTemplate.queryForObject(sql, param, Integer.class);
        } catch (DataAccessException e) {
            System.out.println("Такой книги нет!");
        }
        return 0;
    }

    @Override
    public Book save(Book book, Long authorid, Long libraryid, Genre genre) {
        String insertSql = "insert into books (bookname, year, genreid, title) values(:bookname, :year, :genreid, :title)";
        var result = jdbcTemplate.update(insertSql, Map.of("bookname", book.getBookName(),
                "year", book.getYear(), "genreid", genre.getId(), "title", book.getTitle()));

        var bookId = getBookId(book.getBookName());

        linkBookAndAuthor(bookId, authorid);
        linkBookAndLibrary(bookId, libraryid);
        return result == 1 ? book : null;
    }

    private Long getBookId(String bookName) {
        String findIdSql = "select b.bookid from books b where bookname=:bookname";
        Map<String, String> param = Map.of("bookname", bookName);
        return jdbcTemplate.queryForObject(findIdSql, param, Long.class);
    }

    private void linkBookAndAuthor(Long bookId, Long authorId) {
        String sql = "insert into books_authors(authorid, bookid) values (:authorid, :bookid)";
        Map<String, Long> params = Map.of("authorid", authorId, "bookid", bookId);
        jdbcTemplate.update(sql, params);
    }

    private void linkBookAndLibrary(Long bookId, Long libraryId) {
        String sql = "insert into books_libraries (libraryid, bookid) values(:libraryid, :bookid)";
        Map<String, Long> params = Map.of("libraryid", libraryId, "bookid", bookId);
        jdbcTemplate.update(sql, params);
    }

    @Override
    public void checkIsExsitBook(String bookname) {
        String findSql = "select * from books";
        var books = jdbcTemplate.query(findSql, new BookMapper());
        books.forEach(book1 -> {
            if (book1.getBookName().equals(bookname)) {
                throw new ValidationException("Такая книга уже существует, проверьте свои ввод!");
            }
        });
    }

    @Override
    public List<Book> getBookById(Long bookId) {
        String sql = "select * from books left join books_authors ba on books.bookid = ba.bookid left join books_libraries bl on books.bookid = bl.bookid left join authors a on ba.authorid = a.authorid left join libraries l on bl.libraryid = l.libraryid where books.bookid=:bookid";
        Map<String, Long> param = Map.of("bookid", bookId);
        return jdbcTemplate.query(sql, param, new BookMapperForAll());
    }

    @Override
    public void deleteBookById(Long bookId) {
        String sql = "delete\n" +
                "from books_libraries\n" +
                "where bookid = :bookid;\n" +
                "delete\n" +
                "from books_authors\n" +
                "where bookid = :bookid;\n" +
                "delete\n" +
                "from books\n" +
                "where bookid = :bookid";
        Map<String, Long> param = Map.of("bookid", bookId);
        jdbcTemplate.update(sql, param);
    }

    @Override
    public List<Book> getBooksByAuthorName(String authorName) {
        String sql = """
                select bookname
                from authors
                         left join books_authors ba on authors.authorid = ba.authorid
                         left join books on books.bookid = ba.bookid
                where authorname=:authorname""";
        Map<String, String> params = Map.of("authorname", authorName);
        return jdbcTemplate.query(sql, params, new BookMapper());
    }


    public static class BookMapperForAll implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Set<Author> authors = new HashSet<>();
            Set<Library> libraries = new HashSet<>();
            var authorid = rs.getLong("authorid");
            var authorname = rs.getString("authorname");
            var rating = rs.getInt("rating");
            var yearofbirth = rs.getInt("yearofbirth");
            var biography = rs.getString("biography");
            authors.add(Author.builder().id(authorid).authorName(authorname)
                    .rating(rating).yearOfBirth(yearofbirth).biography(biography).build());

            var bookid = rs.getLong("bookid");
            var bookName = rs.getString("bookname");
            var year = rs.getInt("year");
            var title = rs.getString("title");

            var genreid = rs.getLong("genreid");

            var libraryid = rs.getLong("libraryid");
            var libraryname = rs.getString("libraryname");
            libraries.add(Library.builder().id(libraryid).libraryName(libraryname).build());
            while (rs.next()) {
                authorid = rs.getLong("authorid");
                authorname = rs.getString("authorname");
                rating = rs.getInt("rating");
                yearofbirth = rs.getInt("yearofbirth");
                biography = rs.getString("biography");

                libraryid = rs.getLong("libraryid");
                libraryname = rs.getString("libraryname");

                libraries.add(Library.builder().id(libraryid).libraryName(libraryname).build());
                authors.add(Author.builder().id(authorid).authorName(authorname)
                        .rating(rating).yearOfBirth(yearofbirth).biography(biography).build());
            }

            return Book.builder()
                    .authors(authors)
                    .id(bookid)
                    .bookName(bookName)
                    .year(year)
                    .title(title)
                    .genreId(genreid)
                    .libraries(libraries)
                    .build();
        }
    }

    public static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            var id = rs.getLong("bookid");
            var bookName = rs.getString("bookname");
            var year = rs.getInt("year");
            var genreid = rs.getLong("genreid");
            var title = rs.getString("title");
            return Book.builder().id(id).bookName(bookName).year(year)
                    .title(title).build();
        }
    }
}
