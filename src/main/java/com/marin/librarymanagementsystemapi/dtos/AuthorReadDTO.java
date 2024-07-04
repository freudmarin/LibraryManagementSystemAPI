package com.marin.librarymanagementsystemapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorReadDTO {
    private Long id;
    private String name;
    private String bio;
}
