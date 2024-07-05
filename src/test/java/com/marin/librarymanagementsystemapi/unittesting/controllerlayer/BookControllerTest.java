package com.marin.librarymanagementsystemapi.unittesting.controllerlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marin.librarymanagementsystemapi.TestSecurityConfig;
import com.marin.librarymanagementsystemapi.controllers.BookController;
import com.marin.librarymanagementsystemapi.dtos.AuthorReadDTO;
import com.marin.librarymanagementsystemapi.dtos.BookCreateDTO;
import com.marin.librarymanagementsystemapi.dtos.BookReadDTO;
import com.marin.librarymanagementsystemapi.dtos.BookUpdateDTO;
import com.marin.librarymanagementsystemapi.repositories.BookRepository;
import com.marin.librarymanagementsystemapi.services.books.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@ContextConfiguration(classes = BookController.class)
@Import(TestSecurityConfig.class)
public class BookControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    //Because of UniqueIsbnValidator's constructor, we need to mock BookRepository
    @MockBean
    private BookRepository bookRepository;

    private BookReadDTO bookReadDTO1, bookReadDTO2;
    private BookCreateDTO bookCreateDTO;
    private BookUpdateDTO bookUpdateDTO;

    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);

        // Initialize common objects and DTOs
        bookReadDTO1 = new BookReadDTO(1L, "Book 1", "Description 1", "1234567890", new AuthorReadDTO());
        bookReadDTO2 = new BookReadDTO(2L, "Book 2", "Description 2", "1234567891", new AuthorReadDTO());

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
    void testGetAllBooks() throws Exception {
        List<BookReadDTO> bookList = Arrays.asList(bookReadDTO1, bookReadDTO2);
        when(bookService.getAllBooks()).thenReturn(bookList);

        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Book 1"))
                .andExpect(jsonPath("$[1].title").value("Book 2"));
    }

    @Test
    void testGetBookById() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(bookReadDTO1);

        mockMvc.perform(get("/api/v1/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Book 1"))
                .andExpect(jsonPath("$.description").value("Description 1"));
    }

    @Test
    void testCreateBook() throws Exception {
        when(bookService.createBook(eq(1L), any(BookCreateDTO.class))).thenReturn(bookReadDTO1);

        mockMvc.perform(post("/api/v1/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Book 1"))
                .andExpect(jsonPath("$.description").value("Description 1"));
    }

    @Test
    void testUpdateBook() throws Exception {
        when(bookService.updateBook(eq(1L), eq(1L), any(BookUpdateDTO.class))).thenReturn(bookReadDTO1);

        mockMvc.perform(put("/api/v1/books/1/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Book 1"))
                .andExpect(jsonPath("$.description").value("Description 1"));
    }

    @Test
    void testDeleteBook() throws Exception {
        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/api/v1/books/1"))
                .andExpect(status().isNoContent());
    }
}
