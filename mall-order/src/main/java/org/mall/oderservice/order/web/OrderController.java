package org.mall.oderservice.order.web;

import jakarta.validation.Valid;
import org.mall.oderservice.order.domain.Order;
import org.mall.oderservice.order.domain.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public Flux<Order> get(@AuthenticationPrincipal Jwt jwt) {
        return orderService.getAllOrders(jwt.getSubject());
    }

    @PostMapping
    public Mono<Order> post(@RequestBody @Valid OrderRequest orderRequest) {
        return orderService.submitOrder(orderRequest.isbn(), orderRequest.quantity());
    }
}