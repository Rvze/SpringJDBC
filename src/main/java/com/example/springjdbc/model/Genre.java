package com.example.springjdbc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Genre {
    private Long id;
    private String genreName;
    private String description;
    private Integer ageLimit;
    private Set<Book> books;
}


