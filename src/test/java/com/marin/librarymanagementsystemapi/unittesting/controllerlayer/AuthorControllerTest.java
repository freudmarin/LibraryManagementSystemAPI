package com.marin.librarymanagementsystemapi.unittesting.controllerlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marin.librarymanagementsystemapi.TestSecurityConfig;
import com.marin.librarymanagementsystemapi.controllers.AuthorController;
import com.marin.librarymanagementsystemapi.dtos.AuthorReadDTO;
import com.marin.librarymanagementsystemapi.dtos.AuthorWriteDTO;
import com.marin.librarymanagementsystemapi.services.authors.AuthorService;
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

@WebMvcTest(AuthorController.class)
@ContextConfiguration(classes = AuthorController.class)
@Import(TestSecurityConfig.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;


    private AuthorReadDTO authorReadDTO1, authorReadDTO2;
    private AuthorWriteDTO authorWriteDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Initialize common objects and DTOs
        authorReadDTO1 = new AuthorReadDTO(1L, "Author 1", "Bio1");
        authorReadDTO2 = new AuthorReadDTO(2L, "Author 2", "Bio2");

        authorWriteDTO = new AuthorWriteDTO("Author 1", "Bio1");
    }

    @Test
    void testGetAllAuthors() throws Exception {
        List<AuthorReadDTO> authorList = Arrays.asList(authorReadDTO1, authorReadDTO2);
        when(authorService.getAllAuthors()).thenReturn(authorList);

        mockMvc.perform(get("/api/v1/authors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Author 1"))
                .andExpect(jsonPath("$[1].name").value("Author 2"));
    }

    @Test
    void testGetAuthorById() throws Exception {
        when(authorService.getAuthorById(1L)).thenReturn(authorReadDTO1);

        mockMvc.perform(get("/api/v1/authors/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Author 1"))
                .andExpect(jsonPath("$.bio").value("Bio1"));
    }

    @Test
    void testCreateAuthor() throws Exception {
        when(authorService.createAuthor(any(AuthorWriteDTO.class))).thenReturn(authorReadDTO1);

        mockMvc.perform(post("/api/v1/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authorWriteDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Author 1"))
                .andExpect(jsonPath("$.bio").value("Bio1"));
    }

    @Test
    void testUpdateAuthor() throws Exception {
        when(authorService.updateAuthor(eq(1L), any(AuthorWriteDTO.class))).thenReturn(authorReadDTO1);

        mockMvc.perform(put("/api/v1/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authorWriteDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Author 1"))
                .andExpect(jsonPath("$.bio").value("Bio1"));
    }

    @Test
    void testDeleteAuthor() throws Exception {
        doNothing().when(authorService).deleteAuthor(1L);

        mockMvc.perform(delete("/api/v1/authors/1"))
                .andExpect(status().isNoContent());
    }
}
