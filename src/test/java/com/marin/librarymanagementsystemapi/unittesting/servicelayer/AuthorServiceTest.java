package com.marin.librarymanagementsystemapi.unittesting.servicelayer;

import com.marin.librarymanagementsystemapi.dtos.AuthorReadDTO;
import com.marin.librarymanagementsystemapi.dtos.AuthorWriteDTO;
import com.marin.librarymanagementsystemapi.entities.Author;
import com.marin.librarymanagementsystemapi.repositories.AuthorRepository;
import com.marin.librarymanagementsystemapi.services.authors.AuthorServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    private AuthorServiceImpl authorServiceImpl;


    private Author author1;
    private Author author2;
    private AuthorWriteDTO authorWriteDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize common objects and DTOs
        author1 = new Author(1L, "Author 1", "Bio1", new ArrayList<>());
        author2 = new Author(2L, "Author 2", "Bio2", new ArrayList<>());
        authorWriteDTO = new AuthorWriteDTO("Author 1", "Bio1");
        authorServiceImpl = new AuthorServiceImpl(authorRepository);

    }

    @Test
    void testGetAllAuthors() {
        when(authorRepository.findAll()).thenReturn(Arrays.asList(author1, author2));
        List<AuthorReadDTO> authorsDTOs = authorServiceImpl.getAllAuthors();
        assertNotNull(authorsDTOs);
        assertEquals(2, authorsDTOs.size());
        assertEquals("Author 1", authorsDTOs.get(0).getName());
        assertEquals("Author 2", authorsDTOs.get(1).getName());
        assertEquals("Bio1", authorsDTOs.get(0).getBio());
        assertEquals("Bio2", authorsDTOs.get(1).getBio());
    }

    @Test
    void testGetBookById() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author1));
        AuthorReadDTO authorReadDTO = authorServiceImpl.getAuthorById(1L);

        assertNotNull(authorReadDTO);
        assertEquals("Author 1", authorReadDTO.getName());
        assertEquals("Bio1", authorReadDTO.getBio());
    }

    @Test
    void testGetAuthorById_NotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> authorServiceImpl.getAuthorById(1L));

        String expectedMessage = "Author with id 1 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testCreateAuthor() {
        when(authorRepository.save(any(Author.class))).thenReturn(author1);

        AuthorReadDTO authorReadDTO = authorServiceImpl.createAuthor(authorWriteDTO);
        assertNotNull(authorReadDTO);
        assertEquals("Author 1", authorReadDTO.getName());
        assertEquals("Bio1", authorReadDTO.getBio());
    }


    @Test
    void testUpdateAuthor() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author1));
        when(authorRepository.save(any(Author.class))).thenReturn(author2);
        AuthorReadDTO authorReadDTO = authorServiceImpl.updateAuthor(1L, authorWriteDTO);
        assertNotNull(authorReadDTO);
        assertEquals("Author 2", authorReadDTO.getName());
        assertEquals("Bio2", authorReadDTO.getBio());
    }

    @Test
    void testDeleteBook() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author1));
        authorServiceImpl.deleteAuthor(1L);
        verify(authorRepository, times(1)).deleteById(1L);
    }
}
