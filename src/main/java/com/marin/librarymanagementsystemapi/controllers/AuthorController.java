package com.marin.librarymanagementsystemapi.controllers;

import com.marin.librarymanagementsystemapi.dtos.AuthorReadDTO;
import com.marin.librarymanagementsystemapi.dtos.AuthorWriteDTO;
import com.marin.librarymanagementsystemapi.dtos.BookReadDTO;
import com.marin.librarymanagementsystemapi.responses.ErrorResponse;
import com.marin.librarymanagementsystemapi.services.authors.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Operation(summary = "Get a list of all authors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the list of authors",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AuthorReadDTO.class)))
                    }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})

    })
    @GetMapping
    public ResponseEntity<List<AuthorReadDTO>> getAllAuthors() {
        return new ResponseEntity<>(authorService.getAllAuthors(), HttpStatus.OK);
    }

    @Operation(summary = "Get an author by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the author",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookReadDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Author not found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) )}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("{id}")
    public ResponseEntity<AuthorReadDTO> getAuthorById(@PathVariable Long id) {
        return new ResponseEntity<>(authorService.getAuthorById(id), HttpStatus.OK);
    }

    @Operation(summary = "Create a new author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthorReadDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request, Validation error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) )}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Author not found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping
    public ResponseEntity<AuthorReadDTO> createAuthor(@Valid @RequestBody AuthorWriteDTO authorWriteDTO) {
        return new ResponseEntity<>(authorService.createAuthor(authorWriteDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthorReadDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request, Validation error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) )}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Author not found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })

    @PutMapping("{id}")
    public ResponseEntity<AuthorReadDTO> updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorWriteDTO authorWriteDTO) {
        return new ResponseEntity<>(authorService.updateAuthor(id, authorWriteDTO), HttpStatus.OK);
    }

    @Operation(summary = "Delete an author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Author deleted",
                    content = { @Content(mediaType = "application/json") }) ,
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Author not found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})

    })

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
