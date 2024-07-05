package com.marin.librarymanagementsystemapi.integrationtesting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marin.librarymanagementsystemapi.TestSecurityConfig;
import com.marin.librarymanagementsystemapi.dtos.BookCreateDTO;
import com.marin.librarymanagementsystemapi.dtos.BookUpdateDTO;
import com.marin.librarymanagementsystemapi.entities.Author;
import com.marin.librarymanagementsystemapi.entities.Book;
import com.marin.librarymanagementsystemapi.repositories.AuthorRepository;
import com.marin.librarymanagementsystemapi.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Sql(scripts = "/schema.sql")
@Import(TestSecurityConfig.class)
public class BookControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private Author author1;
    private Book book1;

    @BeforeEach
    void setup() {
        // Initialize authors
        author1 = new Author("Author 1", "Bio 1");
        book1 = new Book("Book 1", "Description 1", "1234567890", author1);
        authorRepository.save(author1);
        bookRepository.save(book1);
    }

    @Test
    void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Book 1"));
    }

    @Test
    void testGetBookById() throws Exception {
        mockMvc.perform(get("/api/v1/books/"+ book1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Book 1"))
                .andExpect(jsonPath("$.description").value("Description 1"))
                .andExpect(jsonPath("$.isbn").value("1234567890"));
    }

    @Test
    void testCreateBook() throws Exception {
        BookCreateDTO bookCreateDTO = new BookCreateDTO();
        bookCreateDTO.setTitle("New Book");
        bookCreateDTO.setDescription("New Description");
        bookCreateDTO.setIsbn("9876543210");

        mockMvc.perform(post("/api/v1/books/" + author1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("New Book"))
                .andExpect(jsonPath("$.description").value("New Description"))
                .andExpect(jsonPath("$.isbn").value("9876543210"));
    }

    @Test
    void testUpdateBook() throws Exception {
        BookUpdateDTO bookUpdateDTO = new BookUpdateDTO();
        bookUpdateDTO.setTitle("Updated Book");
        bookUpdateDTO.setDescription("Updated Description");
        bookUpdateDTO.setIsbn("1122334455");

        mockMvc.perform(put("/api/v1/books/" + book1.getId()+ "/authors/" +author1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Updated Book"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.isbn").value("1122334455"));
    }

    @Test
    void testDeleteBook() throws Exception {
        mockMvc.perform(delete("/api/v1/books/" + book1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
