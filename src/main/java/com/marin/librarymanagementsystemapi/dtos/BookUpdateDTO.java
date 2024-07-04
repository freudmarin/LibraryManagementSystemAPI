package com.marin.librarymanagementsystemapi.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookUpdateDTO {
    @NotEmpty(message = "Title cannot be empty")
    private String title;
    private String description;
    @Size(min = 10, max = 13)
    private String isbn;
}
