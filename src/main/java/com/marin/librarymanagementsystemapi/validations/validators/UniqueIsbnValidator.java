package com.marin.librarymanagementsystemapi.validations.validators;

import com.marin.librarymanagementsystemapi.repositories.BookRepository;
import com.marin.librarymanagementsystemapi.validations.annotations.UniqueIsbn;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class UniqueIsbnValidator implements ConstraintValidator<UniqueIsbn, String> {

    private final BookRepository bookRepository;

    @Setter
    private Long currentBookId;

    public UniqueIsbnValidator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void initialize(UniqueIsbn constraintAnnotation) {
    }

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        if (isbn == null || isbn.isEmpty()) {
            return false;
        }
        if (currentBookId == null) {
            return !bookRepository.existsByIsbn(isbn);
        }

        //update-scenario
        // we consider the isbn unique if it is not used by any other book
        // or it is used by the current book
        return !bookRepository.existsByIsbnAndIdNot(isbn, currentBookId);
    }
}
