package com.marin.librarymanagementsystemapi.validations.annotations;

import com.marin.librarymanagementsystemapi.validations.validators.UniqueIsbnValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueIsbnValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueIsbn {
    String message() default "ISBN must be unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
