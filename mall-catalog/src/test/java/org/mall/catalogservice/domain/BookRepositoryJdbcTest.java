package org.mall.catalogservice.domain;


import org.junit.jupiter.api.Test;
import org.mall.catalogservice.conf.DatabaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Import(DatabaseConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
class BookRepositoryJdbcTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JdbcAggregateTemplate jdbcAggregateTemplate;

    @Test
    void findBookByIsbnWhenExisting() {
        var isbn = "123";
        var book = Book.of(isbn, "title", "author", 12.90);
        jdbcAggregateTemplate.insert(book);
        var actualBook = bookRepository.findByIsbn(isbn);
        assertThat(actualBook).isPresent();
        assertThat(actualBook.get().isbn()).isEqualTo(isbn);
    }

    @Test
    void whenCreateBooNotAuthenticationThenNoAuditMetadata() {
        var bookToCreate = Book.of("123456", "title", "author", 9.9);
        var createdBook = bookRepository.save(bookToCreate);
        assertThat(createdBook.createdBy()).isNull();
        assertThat(createdBook.lastModifiedBy()).isNull();
    }

    @Test
    @WithMockUser("yingtingxu")
    void whenCreateBooAuthenticationThenHasAuditMetadata() {
        var bookToCreate = Book.of("123456", "title", "author", 9.9);
        var createdBook = bookRepository.save(bookToCreate);
        assertThat(createdBook.createdBy()).isEqualTo("yingtingxu");
        assertThat(createdBook.lastModifiedBy()).isEqualTo("yingtingxu");
    }
}