package org.mall.catalogservice.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BookValidationTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsCorrectThenValidationSucceeds() {
        var book = Book.of("1234567891", "title", "author", 9.90);
        Set<ConstraintViolation<Book>> validations = validator.validate(book);
        assertThat(validations).isEmpty();
    }

    @Test
    void whenIsbnDefinedButIncorrectThenValidationFails() {
        var book = Book.of("a23456", "title", "author", 9.90);
        Set<ConstraintViolation<Book>> validations = validator.validate(book);
        assertThat(validations).hasSize(1);
        assertThat(validations.iterator().next().getMessage())
                .isEqualTo("The ISBN format bust be ISBN-10 or ISBN-13");
    }
}