package com.marin.librarymanagementsystemapi.services.books;

import com.marin.librarymanagementsystemapi.dtos.BookCreateDTO;
import com.marin.librarymanagementsystemapi.dtos.BookReadDTO;
import com.marin.librarymanagementsystemapi.dtos.BookUpdateDTO;
import com.marin.librarymanagementsystemapi.dtos.DTOMappings;
import com.marin.librarymanagementsystemapi.entities.Author;
import com.marin.librarymanagementsystemapi.entities.Book;
import com.marin.librarymanagementsystemapi.repositories.AuthorRepository;
import com.marin.librarymanagementsystemapi.repositories.BookRepository;
import com.marin.librarymanagementsystemapi.validations.validators.UniqueIsbnValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final UniqueIsbnValidator uniqueIsbnValidator;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, UniqueIsbnValidator uniqueIsbnValidator) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.uniqueIsbnValidator = uniqueIsbnValidator;
    }

    @Override
    public List<BookReadDTO> getAllBooks() {
        return bookRepository.findAll().stream().map(DTOMappings.INSTANCE::toBookReadDTO).toList();
    }

    @Override
    public BookReadDTO getBookById(Long id) {
        return bookRepository.findById(id).map(DTOMappings.INSTANCE::toBookReadDTO).
                orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " not found"));
    }

    @Override
    public BookReadDTO createBook(Long authorId, BookCreateDTO bookWriteDTO) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new EntityNotFoundException("Author with id " + authorId + " not found"));
        Book newBook = DTOMappings.INSTANCE.toBook(bookWriteDTO);
        newBook.setAuthor(author);
        return DTOMappings.INSTANCE.toBookReadDTO(bookRepository.save(newBook));
    }

    @Override
    public BookReadDTO updateBook(Long id, Long authorId, BookUpdateDTO bookUpdateDTO) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new EntityNotFoundException("Author with id " + authorId + " not found"));
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " not found"));
        existingBook.setTitle(bookUpdateDTO.getTitle());
        existingBook.setDescription(bookUpdateDTO.getDescription());
        uniqueIsbnValidator.setCurrentBookId(id);
        if (!uniqueIsbnValidator.isValid(bookUpdateDTO.getIsbn(), null)) {
            throw new IllegalArgumentException("ISBN already exists: " + bookUpdateDTO.getIsbn());
        }
        existingBook.setIsbn(bookUpdateDTO.getIsbn());
        existingBook.setAuthor(author);
        return DTOMappings.INSTANCE.toBookReadDTO(bookRepository.save(existingBook));
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " was not found in the system, hence cannot be deleted."));
        bookRepository.deleteById(id);
    }
}
