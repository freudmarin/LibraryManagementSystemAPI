package com.marin.librarymanagementsystemapi.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorWriteDTO {

    @NotEmpty(message = "Name cannot be empty")
    private String name;
    private String bio;

}
