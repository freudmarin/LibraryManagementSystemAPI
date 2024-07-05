package com.marin.librarymanagementsystemapi.unittesting.servicelayer;

import com.marin.librarymanagementsystemapi.dtos.BookCreateDTO;
import com.marin.librarymanagementsystemapi.dtos.BookReadDTO;
import com.marin.librarymanagementsystemapi.dtos.BookUpdateDTO;
import com.marin.librarymanagementsystemapi.entities.Author;
import com.marin.librarymanagementsystemapi.entities.Book;
import com.marin.librarymanagementsystemapi.repositories.AuthorRepository;
import com.marin.librarymanagementsystemapi.repositories.BookRepository;
import com.marin.librarymanagementsystemapi.services.books.BookServiceImpl;
import com.marin.librarymanagementsystemapi.validations.validators.UniqueIsbnValidator;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private UniqueIsbnValidator uniqueIsbnValidator;

    private BookServiceImpl bookServiceImpl;

    private Book book1, book2;

    private BookCreateDTO bookCreateDTO;
    private BookUpdateDTO bookUpdateDTO;
    private Author author1;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize common objects and DTOs
        author1 = new Author(1L, "Author 1", "Bio1", new ArrayList<>());

        book1 = new Book(1L, "Test Book 1", "Test Description 1", "1234567890", new Author());
        book2 = new Book(2L, "Test Book 2", "Test Description 2", "1234567891", new Author());
        bookServiceImpl = new BookServiceImpl(bookRepository, authorRepository, uniqueIsbnValidator);

        bookCreateDTO = new BookCreateDTO();
        bookCreateDTO.setTitle("New Book");
        bookCreateDTO.setDescription("New Description");
        bookCreateDTO.setIsbn("9876543210");

        bookUpdateDTO = new BookUpdateDTO();
        bookUpdateDTO.setTitle("Updated Book");
        bookUpdateDTO.setDescription("Updated Description");
        bookUpdateDTO.setIsbn("1122334455");
    }

    @Test
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));
        List<BookReadDTO> bookDTOs = bookServiceImpl.getAllBooks();
        assertNotNull(bookDTOs);
        assertEquals(2, bookDTOs.size());
        assertEquals("Test Book 1", bookDTOs.get(0).getTitle());
        assertEquals("Test Book 2", bookDTOs.get(1).getTitle());
        assertEquals("Test Description 1", bookDTOs.get(0).getDescription());
        assertEquals("Test Description 2", bookDTOs.get(1).getDescription());
        assertEquals("1234567890", bookDTOs.get(0).getIsbn());
        assertEquals("1234567891", bookDTOs.get(1).getIsbn());
    }

    @Test
    void testGetBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        BookReadDTO bookDTO = bookServiceImpl.getBookById(1L);

        assertNotNull(bookDTO);
        assertEquals("Test Book 1", bookDTO.getTitle());
        assertEquals("Test Description 1", bookDTO.getDescription());
        assertEquals("1234567890", bookDTO.getIsbn());
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> bookServiceImpl.getBookById(1L));

        String expectedMessage = "Book with id 1 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testCreateBook() {

        Book book = new Book();
        book.setTitle("Test Book");
        book.setDescription("Test Description");
        book.setIsbn("1234567890");

        BookCreateDTO bookCreateDTO = new BookCreateDTO();
        bookCreateDTO.setTitle("Test Book");
        bookCreateDTO.setDescription("Test Description");
        bookCreateDTO.setIsbn("1234567890");
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author1));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookReadDTO bookDTO = bookServiceImpl.createBook(1L, bookCreateDTO);

        assertNotNull(bookDTO);
        assertEquals("Test Book", bookDTO.getTitle());
    }

    @Test
    void testCreateBook_AuthorNotFound() {
        BookCreateDTO bookCreateDTO = new BookCreateDTO();
        bookCreateDTO.setTitle("Test Book");

        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> bookServiceImpl.createBook(1L, bookCreateDTO));

        String expectedMessage = "Author with id 1 not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateBook() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Old Title");
        book.setDescription("Old Description");

        BookUpdateDTO bookUpdateDTO = new BookUpdateDTO();
        bookUpdateDTO.setTitle("New Title");
        bookUpdateDTO.setDescription("New Description");
        bookUpdateDTO.setIsbn("1234567890");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author1));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(uniqueIsbnValidator.isValid(anyString(), any())).thenReturn(true);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookReadDTO bookDTO = bookServiceImpl.updateBook(1L, 1L, bookUpdateDTO);

        assertNotNull(bookDTO);
        assertEquals("New Title", bookDTO.getTitle());
    }

    @Test
    void testUpdateBook_BookNotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author1));
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> bookServiceImpl.updateBook(1L, 1L, bookUpdateDTO));

        String expectedMessage = "Book with id 1 not found";
        String actualMessage = exception.getMessage();

        System.out.println(actualMessage);

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeleteBook() {
        bookServiceImpl.deleteBook(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }
}
