package com.example.springjdbc.shell;

import com.example.springjdbc.exception.ValidationException;
import com.example.springjdbc.model.Author;
import com.example.springjdbc.model.Book;
import com.example.springjdbc.model.Genre;
import com.example.springjdbc.model.Library;
import com.example.springjdbc.service.AuthorService;
import com.example.springjdbc.service.BookService;
import com.example.springjdbc.service.GenreService;
import com.example.springjdbc.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@ShellComponent
@RequiredArgsConstructor
public class LibraryEventCommands {
    private final AuthorService authorService;
    private final BookService bookService;
    private final GenreService genreService;
    private final LibraryService libraryService;
    private final LineReader reader = LineReaderBuilder.builder().build();

    public String ask(String question) {
        return this.reader.readLine("\n" + question + " > ");
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

        return authorService.save(author);
    }

    @ShellMethod(value = "Get count of authors", key = {"c", "count"})
    public int countOfAuthors() {
        return authorService.count();
    }

    @ShellMethod(value = "Get count of books the author has", key = {"bc", "book count"})
    public int countOfBooksTheAuthorHas(@ShellOption String authorName) {
        return authorService.countOfBooks(authorName);
    }

    @ShellMethod(value = "Get a author rating", key = {"ar", "author rate"})
    public int getTheAuthorRating(@ShellOption String authorName) {
        return authorService.getRating(authorName);
    }

    @ShellMethod(value = "Get a author bio", key = {"bio"})
    public String getTheAuthorBio(@ShellOption String authorName) {
        return authorService.getBiography(authorName);
    }

    @ShellMethod(value = "Get a author books", key = {"ab"})
    public List<Book> getAuthorBooks(@ShellOption String authorName) {
        return bookService.getBooksByAuthorName(authorName);
    }

    @ShellMethod(value = "Get a book genre", key = {"bg", "book genre"})
    public Genre getBookGenre(@ShellOption String bookName) {
        return genreService.getGenreByBookName(bookName);
    }

    @ShellMethod(value = "Get age limit of book", key = {"al", "age limit"})
    public Integer getAgeLimitOfBook(@ShellOption String bookName) {
        return bookService.getBookAgeLimit(bookName);
    }

    @ShellMethod(value = "Get authors name of book", key = {"an", "authors name"})
    public List<Author> getAuthorsOfBook(@ShellOption(arity = 2) String bookName) {
        return authorService.getAuthorsByBookName(bookName);
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
        bookService.checkIsExistBook(bookName);
        String bookYear = this.ask(q2);
        String bookTitle = this.ask(q5);
        String genrename = this.ask(q3);


        Genre genre;
        var genreId = genreService.checkIsExist(genrename);
        if (genreId == null) {
            String description = this.ask(g1);
            String agelimit = this.ask(g2);
            genre = Genre.builder().genreName(genrename).ageLimit(Integer.valueOf(agelimit)).description(description).build();
            genre = genreService.save(genre);
        } else {
            genre = genreService.findById(genreId);
        }
        String authorname = this.ask(q4);
        var findedAuthorId = authorService.getAuthor(authorname);
        if (Objects.equals(findedAuthorId, null)) {
            throw new ValidationException("Такого автора в нашей базе не существует! Сначала добавьте его");
        }
        String libraryname = this.ask(l1);

        var libraryid = libraryService.getOrSaveLibrary(libraryname);

        Book book = Book.builder().bookName(bookName).year(Integer.valueOf(bookYear)).title(bookTitle).build();

        return bookService.save(book, findedAuthorId.getId(), libraryid, genre);
    }

    @ShellMethod(value = "get book by id", key = {"getb", "get book"})
    public Optional<Book> getBookById(@ShellOption Long bookId) {
        return bookService.getBookById(bookId);
    }

    @ShellMethod(value = "delete book by id", key = {"delb", "delete book"})
    public void deleteBookById(@ShellOption Long bookId) {
        bookService.deleteBookById(bookId);
    }

    @ShellMethod(value = "get library where book exist", key = "gl")
    public Set<Library> getLibraries(@ShellOption(arity = 2) String bookname) {
        return libraryService.getLibrariesByBookName(bookname);
    }

    @ShellMethod(value = "get count of books in library", key = "cbl")
    public Integer getCountOfBooksFromLibrary(@ShellOption String libraryName) {
        return libraryService.countOfBooks(libraryName);
    }

}
