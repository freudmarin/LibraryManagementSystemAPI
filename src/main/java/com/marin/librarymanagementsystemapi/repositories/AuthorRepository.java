package com.marin.librarymanagementsystemapi.repositories;

import com.marin.librarymanagementsystemapi.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
