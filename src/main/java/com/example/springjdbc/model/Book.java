package com.example.springjdbc.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

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