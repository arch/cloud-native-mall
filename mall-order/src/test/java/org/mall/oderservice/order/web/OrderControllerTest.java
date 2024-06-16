package org.mall.oderservice.order.web;

import org.junit.jupiter.api.Test;
import org.mall.oderservice.conf.SecurityConfiguration;
import org.mall.oderservice.order.domain.Order;
import org.mall.oderservice.order.domain.OrderService;
import org.mall.oderservice.order.domain.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@WebFluxTest(OrderController.class)
@Import(SecurityConfiguration.class)
class OrderControllerTest {
    @Autowired
    private WebTestClient webClient;

    @MockBean
    private OrderService orderService;

    @MockBean
    ReactiveJwtDecoder reactiveJwtDecoder;

    @Test
    void whenBookNotAvailableThenRejectedOrder() {
        var orderRequest = new OrderRequest("1234567890",3);
        var expectedOrder = OrderService.buildRejectedOrder(
                orderRequest.isbn(), orderRequest.quantity());

        given(orderService.submitOrder(orderRequest.isbn(), orderRequest.quantity()))
                .willReturn(Mono.just(expectedOrder));

        webClient.mutateWith(SecurityMockServerConfigurers
                        .mockJwt()
                        .authorities(new SimpleGrantedAuthority("ROLE_customer")))
                .post()
                .uri("/orders")
                .bodyValue(orderRequest)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Order.class).value(actualOrder -> {
                   assertThat(actualOrder).isNotNull();
                   assertThat(actualOrder.status()).isEqualTo(OrderStatus.REJECTED);
                });
    }

}