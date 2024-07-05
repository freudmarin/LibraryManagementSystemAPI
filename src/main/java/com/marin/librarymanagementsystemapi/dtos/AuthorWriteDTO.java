package com.marin.librarymanagementsystemapi.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorWriteDTO {

    @NotBlank(message = "Name cannot be empty")
    private String name;
    private String bio;

}
