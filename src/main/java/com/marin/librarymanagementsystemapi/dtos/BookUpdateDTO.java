package com.marin.librarymanagementsystemapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookUpdateDTO {
    @NotBlank(message = "Title cannot be empty")
    private String title;
    private String description;
    @Size(min = 10, max = 13)
    private String isbn;
}
