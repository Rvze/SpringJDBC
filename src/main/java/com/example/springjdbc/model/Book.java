package com.example.springjdbc.model;

import com.example.springjdbc.exception.BookYearException;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Builder
@Data
public class Book {

    private Long id;
    private String bookName;
    private Set<Author> authors;
    private Integer year;
    private String title;
    private Set<Library> libraries;
    private Long genreId;

}