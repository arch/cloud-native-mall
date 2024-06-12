package org.mall.oderservice.book;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class BookClient {
    private final WebClient webClient;

    public BookClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Book> getBookByIsbn(String isbn) {
        return webClient
                .get()
                .uri("/books/{isbn}", isbn)
                .retrieve()
                .bodyToMono(Book.class);
    }
}