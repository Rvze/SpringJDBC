package com.example.springjdbc.shell;

import com.example.springjdbc.dao.AuthorDao;
import com.example.springjdbc.dao.BookDao;
import com.example.springjdbc.dao.GenreDao;
import com.example.springjdbc.dao.LibraryDao;
import com.example.springjdbc.exception.ValidationException;
import com.example.springjdbc.model.Author;
import com.example.springjdbc.model.Book;
import com.example.springjdbc.model.Genre;
import com.example.springjdbc.model.Library;
import org.jline.reader.LineReader;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@ShellComponent
public class LibraryEventCommands {
    private final AuthorDao authorDao;
    private final BookDao bookDao;
    private final GenreDao genreDao;
    private final LibraryDao libraryDao;
    private final LineReader reader;

    public LibraryEventCommands(AuthorDao authorDao, BookDao bookDao, GenreDao genreDao, LibraryDao libraryDao, @Lazy LineReader lineReader) {
        this.authorDao = authorDao;
        this.reader = lineReader;
        this.bookDao = bookDao;
        this.genreDao = genreDao;
        this.libraryDao = libraryDao;
    }

    private String login;

    public String ask(String question) {
        return this.reader.readLine("\n" + question + " > ");
    }

    @ShellMethod(value = "Login Command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "Anonymous") String login) {
        this.login = login;
        return String.format("Приветствую, %s!", login);
    }

    @ShellMethod(value = "Save Author", key = {"sa", "save -a"})
    public Author saveAuthor() {
        String q1 = "Type a author name: ";
        String q2 = "Type a author rating: ";
        String q3 = "Type a author year of birth: ";
        String q4 = "Type a author biography: ";

        String authorname = this.ask(q1);
        String rating = this.ask(q2);
        String yearOfBirth = this.ask(q3);
        String biography = this.ask(q4);
        Author author = new Author();
        author.setAuthorName(authorname);
        author.setRating(Integer.valueOf(rating));
        author.setYearOfBirth(Integer.valueOf(yearOfBirth));
        author.setBiography(biography);

        return authorDao.save(author);
    }

    @ShellMethod(value = "Get count of authors", key = {"c", "count"})
    public int countOfAuthors() {
        return authorDao.count();
    }

    @ShellMethod(value = "Get count of books the author has", key = {"bc", "book count"})
    public int countOfBooksTheAuthorHas(@ShellOption String authorName) {
        return authorDao.countOfBooks(authorName);
    }

    @ShellMethod(value = "Get a author rating", key = {"ar", "author rate"})
    public int getTheAuthorRating(@ShellOption String authorName) {
        return authorDao.getRating(authorName);
    }

    @ShellMethod(value = "Get a author bio", key = {"bio"})
    public String getTheAuthorBio(@ShellOption String authorName) {
        return authorDao.getBiography(authorName);
    }

    @ShellMethod(value = "Get a author books", key = {"ab"})
    public List<String> getAuthorBooks(@ShellOption String authorName) {
        return authorDao.getBooks(authorName);
    }

    @ShellMethod(value = "Get a book genre", key = {"bg", "book genre"})
    public String getBookGenre(@ShellOption String bookName) {
        return bookDao.getGenre(bookName);
    }

    @ShellMethod(value = "Get age limit of book", key = {"al", "age limit"})
    public Integer getAgeLimitOfBook(@ShellOption String bookName) {
        return bookDao.getAgeLimit(bookName);
    }

    @ShellMethod(value = "Get authors name of book", key = {"an", "authors name"})
    public List<String> getAuthorsOfBook(@ShellOption(arity = 2) String bookName) {
        return bookDao.getAuthors(bookName);
    }

    @ShellMethod(value = "Save book", key = {"sb", "save book"})
    public Book saveBook() {
        String q1 = "Type a book name: ";
        String q2 = "Type a book year: ";
        String q3 = "Type a genre name: ";
        String q4 = "Type a author name: ";
        String q5 = "Type a book title: ";

        String g1 = "Type a genre description: ";
        String g2 = "Type a age limit: ";

        String l1 = "Type a library name: ";

        String bookName = this.ask(q1);
        bookDao.checkIsExsitBook(bookName);
        String bookYear = this.ask(q2);
        String bookTitle = this.ask(q5);
        String genrename = this.ask(q3);


        Genre genre;
        var genreId = genreDao.checkIsExist(genrename);
        if (genreId == null) {
            String description = this.ask(g1);
            String agelimit = this.ask(g2);
            genre = Genre.builder().genreName(genrename).ageLimit(Integer.valueOf(agelimit)).description(description).build();
            genre = genreDao.save(genre);
        } else {
            genre = genreDao.findById(genreId);
        }
        String authorname = this.ask(q4);
        var findedAuthorId = authorDao.getAuthor(authorname);
        if (Objects.equals(findedAuthorId, null)) {
            throw new ValidationException("Такого автора в нашей базе не существует! Сначала добавьте его");
        }
        String libraryname = this.ask(l1);

        var libraryid = libraryDao.getOrSaveLibrary(libraryname);

        Book book = Book.builder().bookName(bookName).year(Integer.valueOf(bookYear)).title(bookTitle).build();

        return bookDao.save(book, findedAuthorId, libraryid, genre);
    }

    @ShellMethod(value = "get book by id", key = {"getb", "get book"})
    public List<Book> getBookById(@ShellOption Long bookId) {
        return bookDao.getBookById(bookId);
    }

    @ShellMethod(value = "delete book by id", key = {"delb", "delete book"})
    public void deleteBookById(@ShellOption Long bookId) {
        bookDao.deleteBookById(bookId);
    }

    @ShellMethod(value = "get library where book exist", key = "gl")
    public Set<String> getLibraries(@ShellOption(arity = 2) String bookname) {
        return bookDao.getLibraries(bookname);
    }

    @ShellMethod(value = "get count of books in library", key = "cbl")
    public Integer getCountOfBooksFromLibrary(@ShellOption String libraryName) {
        return libraryDao.countOfBooks(libraryName);
    }

}
