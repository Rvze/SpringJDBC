package com.example.springjdbc.model;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Library {
    private Long id;
    private String libraryName;
    private Set<Book> books;
}
