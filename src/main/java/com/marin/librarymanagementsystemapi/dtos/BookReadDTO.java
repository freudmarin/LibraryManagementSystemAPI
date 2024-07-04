package com.marin.librarymanagementsystemapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookReadDTO {
    private Long id;
    private String title;
    private String description;
    private String isbn;
    private AuthorReadDTO author;
}
