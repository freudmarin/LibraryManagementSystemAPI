package com.marin.librarymanagementsystemapi.integrationtesting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marin.librarymanagementsystemapi.TestSecurityConfig;
import com.marin.librarymanagementsystemapi.dtos.AuthorWriteDTO;
import com.marin.librarymanagementsystemapi.entities.Author;
import com.marin.librarymanagementsystemapi.repositories.AuthorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Sql(scripts = "/schema.sql")
@Import(TestSecurityConfig.class)
public class AuthorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthorRepository authorRepository;

    private Author author1;
    private Author author2;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("server.port", () -> "8081");  // Unique port for this test class
    }

    @Test
    void contextLoads() throws InterruptedException {
        // Keep the test running for a while to access the H2 console
        Thread.sleep(60000); // 60 seconds
    }

    @BeforeEach
    void setup() {
        // Initialize authors
        author1 = new Author("Author 1", "Bio 1");
        author2 = new Author("Author 2", "Bio 2");
        authorRepository.save(author1);
        authorRepository.save(author2);
    }

    @AfterEach
    void tearDown() {
        //authorRepository.deleteAll();
    }

    @Test
    void testGetAllAuthors() throws Exception {
        mockMvc.perform(get("/api/v1/authors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Author 1"))
                .andExpect(jsonPath("$[1].name").value("Author 2"));
    }

    @Test
    void testCreateAuthor() throws Exception {
       AuthorWriteDTO authorWriteDTO = new AuthorWriteDTO();
       authorWriteDTO.setName("New Author");
       authorWriteDTO.setBio("New Bio");

        mockMvc.perform(post("/api/v1/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authorWriteDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("New Author"))
                .andExpect(jsonPath("$.bio").value("New Bio"));
    }

    @Test
    void testGetAuthorById() throws Exception {
        mockMvc.perform(get("/api/v1/authors/" + author1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Author 1"))
                .andExpect(jsonPath("$.bio").value("Bio 1"));
    }

    @Test
    void testUpdateAuthor() throws Exception {
        AuthorWriteDTO authorWriteDTO = new AuthorWriteDTO();
        authorWriteDTO.setName("Updated Author");
        authorWriteDTO.setBio("Updated Bio");

        mockMvc.perform(put("/api/v1/authors/" + author1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorWriteDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Updated Author"))
                .andExpect(jsonPath("$.bio").value("Updated Bio"));
    }

    @Test
    void testDeleteAuthor() throws Exception {
        mockMvc.perform(delete("/api/v1/authors/" + author1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
