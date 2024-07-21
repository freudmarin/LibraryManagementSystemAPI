package com.marin.librarymanagementsystemapi.controllers;


import com.marin.librarymanagementsystemapi.dtos.BookCreateDTO;
import com.marin.librarymanagementsystemapi.dtos.BookReadDTO;
import com.marin.librarymanagementsystemapi.dtos.BookUpdateDTO;
import com.marin.librarymanagementsystemapi.responses.ErrorResponse;
import com.marin.librarymanagementsystemapi.services.books.BookService;
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
@RequestMapping("api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Get a list of all books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the list of books",
                    content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BookReadDTO.class)))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})

    })
    @GetMapping()
    public ResponseEntity<List<BookReadDTO>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @Operation(summary = "Get a book by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookReadDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) )}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("{id}")
    public ResponseEntity<BookReadDTO> getBookById(@PathVariable Long id) {
        return new  ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }

    @Operation(summary = "Create a new book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookReadDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) )}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Author not found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })

    @PostMapping("{authorId}")
    public ResponseEntity<BookReadDTO> createBook(@PathVariable Long authorId, @Valid @RequestBody BookCreateDTO bookWriteDTO) {
        return new ResponseEntity<>(bookService.createBook(authorId, bookWriteDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookReadDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request, Validation error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Author and/or book not found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })

    @PutMapping("{id}/authors/{authorId}")
    public ResponseEntity<BookReadDTO> updateBook(@PathVariable Long id, @PathVariable Long authorId, @Valid @RequestBody BookUpdateDTO bookUpdateDTO) {
        return new ResponseEntity<>(bookService.updateBook(id, authorId, bookUpdateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Delete a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book deleted",
                    content = { @Content(mediaType = "application/json")}) ,
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
