package com.marin.librarymanagementsystemapi.services.books;

import com.marin.librarymanagementsystemapi.dtos.BookCreateDTO;
import com.marin.librarymanagementsystemapi.dtos.BookReadDTO;
import com.marin.librarymanagementsystemapi.dtos.BookUpdateDTO;

import java.util.List;

public interface BookService {
    List<BookReadDTO> getAllBooks();
    BookReadDTO getBookById(Long id);
    BookReadDTO createBook(Long authorId, BookCreateDTO bookWriteDTO);
    BookReadDTO updateBook(Long id,Long authorId, BookUpdateDTO bookWriteDTO);
    void deleteBook(Long id);
}
