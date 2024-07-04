package com.marin.librarymanagementsystemapi.controllers;

import com.marin.librarymanagementsystemapi.dtos.AuthorReadDTO;
import com.marin.librarymanagementsystemapi.dtos.AuthorWriteDTO;
import com.marin.librarymanagementsystemapi.services.authors.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping()
    public ResponseEntity<List<AuthorReadDTO>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @GetMapping("{id}")
    public ResponseEntity<AuthorReadDTO> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @PostMapping()
    public ResponseEntity<AuthorReadDTO> createAuthor(@Valid @RequestBody AuthorWriteDTO authorWriteDTO) {
        return ResponseEntity.ok(authorService.createAuthor(authorWriteDTO));
    }

    @PutMapping("{id}")
    public ResponseEntity<AuthorReadDTO> updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorWriteDTO authorWriteDTO) {
        return ResponseEntity.ok(authorService.updateAuthor(id, authorWriteDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
