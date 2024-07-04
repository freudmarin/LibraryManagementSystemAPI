package com.marin.librarymanagementsystemapi.dtos;

import com.marin.librarymanagementsystemapi.validations.annotations.UniqueIsbn;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookCreateDTO {
    @NotEmpty(message = "Title cannot be empty")
    private String title;
    private String description;
    @Size(min = 10, max = 13)
    @UniqueIsbn
    private String isbn;
}
