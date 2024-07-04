package com.marin.librarymanagementsystemapi.services.authors;

import com.marin.librarymanagementsystemapi.dtos.AuthorReadDTO;
import com.marin.librarymanagementsystemapi.dtos.AuthorWriteDTO;

import java.util.List;

public interface AuthorService {
    List<AuthorReadDTO> getAllAuthors();
    AuthorReadDTO  getAuthorById(Long id);
    AuthorReadDTO createAuthor(AuthorWriteDTO authorWriteDTO);
    AuthorReadDTO updateAuthor(Long id, AuthorWriteDTO authorWriteDTO);
    void deleteAuthor(Long id);
}
