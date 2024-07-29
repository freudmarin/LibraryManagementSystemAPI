package com.marin.librarymanagementsystemapi.services.authors;

import com.marin.librarymanagementsystemapi.dtos.AuthorReadDTO;
import com.marin.librarymanagementsystemapi.dtos.AuthorWriteDTO;
import com.marin.librarymanagementsystemapi.dtos.DTOMappings;
import com.marin.librarymanagementsystemapi.entities.Author;
import com.marin.librarymanagementsystemapi.repositories.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorReadDTO> getAllAuthors() {
        return authorRepository.findAll().stream().map(DTOMappings.INSTANCE::toAuthorReadDTO).toList();
    }

    @Override
    public AuthorReadDTO getAuthorById(Long id) {
        return authorRepository.findById(id).map(DTOMappings.INSTANCE::toAuthorReadDTO)
        .orElseThrow(() -> new EntityNotFoundException("Author with id " + id + " not found"));
    }

    @Override
    public AuthorReadDTO createAuthor(AuthorWriteDTO authorWriteDTO) {
        Author author = DTOMappings.INSTANCE.toAuthor(authorWriteDTO);
        return DTOMappings.INSTANCE.toAuthorReadDTO(authorRepository.save(author));
    }

    @Override
    public AuthorReadDTO updateAuthor(Long id, AuthorWriteDTO authorWriteDTO) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with id " + id + " not found"));
        author.setName(authorWriteDTO.getName());
        author.setBio(authorWriteDTO.getBio());
        return DTOMappings.INSTANCE.toAuthorReadDTO(authorRepository.save(author));
    }

    @Override
    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with id " + id + " was not found in the system, hence cannot be deleted."));
        authorRepository.delete(author);
    }
}
