package com.example.springjdbc.model;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Author {
    private Long id;
    private String authorName;
    private Set<Book> books;
    private Integer rating;
    private Integer yearOfBirth;
    private String biography;

}
