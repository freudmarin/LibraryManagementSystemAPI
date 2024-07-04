package com.marin.librarymanagementsystemapi.repositories;

import com.marin.librarymanagementsystemapi.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
     boolean existsByIsbn(String isbn);
     boolean existsByIsbnAndIdNot(String isbn, Long id);
}
