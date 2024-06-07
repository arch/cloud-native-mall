package org.mall.catalogservice.web;

import org.junit.jupiter.api.Test;
import org.mall.catalogservice.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookJsonTest {
    @Autowired
    private JacksonTester<Book> json;

    @Test
    void testSerialize() throws Exception {
        var book = new Book(1L, "1234567890", "title", "author", 9.90, Instant.now(), Instant.now(),0);
        var jsonContent = json.write(book);
        assertThat(jsonContent)
                .extractingJsonPathStringValue("@.isbn")
                .isEqualTo(book.isbn());
        assertThat(jsonContent)
                .extractingJsonPathStringValue("@.title")
                .isEqualTo(book.title());
        assertThat(jsonContent)
                .extractingJsonPathStringValue("@.author")
                .isEqualTo(book.author());
        assertThat(jsonContent)
                .extractingJsonPathNumberValue("@.price")
                .isEqualTo(book.price());
    }

    @Test
    void testDeserialize() throws Exception {
        var content = """
                {
                  "id": 1,
                  "isbn": "1234567890",
                  "title": "title",
                  "author": "author",
                  "price": 9.90,
                  "version": 0
                }
                """;
        assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(new Book(1L, "1234567890", "title", "author", 9.90, null, null, 0));
    }
}