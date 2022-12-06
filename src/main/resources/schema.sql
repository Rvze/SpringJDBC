drop table if exists authors, books, genres, authors_libraries, libraries, books_libraries, books_authors;
create table authors
(
    authorid    serial primary key,
    authorname  varchar(255) not null,
    rating      int,
    yearofbirth int          not null,
    biography   text

);
create table genres
(
    genreid     serial primary key,
    genrename   varchar(255),
    description text,
    agelimit    int
);

create table books
(
    bookid   serial primary key,
    bookname varchar(255),
    year     int,
    genreid  serial references genres (genreid),
    title varchar(255)
);

create table libraries
(
    libraryid   serial primary key,
    libraryname varchar(255)
);

create table books_libraries
(
    bookslibrariesid serial primary key,
    libraryid        serial references libraries (libraryid),
    bookid           serial references books (bookid)
);

create table books_authors
(
    booksauthorsid serial primary key,
    authorid       serial references authors (authorid),
    bookid         serial references books (bookid)
)

