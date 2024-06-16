package org.mall.oderservice.order.domain;

import org.mall.oderservice.book.Book;
import org.mall.oderservice.book.BookClient;
import org.mall.oderservice.order.event.OrderAcceptedMessage;
import org.mall.oderservice.order.event.OrderDispatchedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final BookClient bookClient;
    private final OrderRepository orderRepository;
    private final StreamBridge streamBridge;

    public OrderService(BookClient bookClient, OrderRepository orderRepository, StreamBridge streamBridge) {
        this.bookClient = bookClient;
        this.orderRepository = orderRepository;
        this.streamBridge = streamBridge;
    }

    public Flux<Order> getAllOrders(String username) {
        return orderRepository.findByCreatedBy(username);
    }

    // NOTE: spring.stream.rabbit.bindings.<name>.producer.transacted=true
    @Transactional
    public Mono<Order> submitOrder(String isbn, int quantity) {
        return bookClient.getBookByIsbn(isbn)
                .map(book -> buildAcceptedOrder(book, quantity))
                .defaultIfEmpty(buildRejectedOrder(isbn, quantity))
                .flatMap(orderRepository::save)
                .doOnNext(this::publishOrderAcceptedEvent);
    }

    public static Order buildAcceptedOrder(Book book, int quantity) {
        return Order.of(book.isbn(), book.title() + " - " + book.author(), book.price(), quantity, OrderStatus.ACCEPTED);
    }

    public static Order buildRejectedOrder(String isbn, int quantity) {
        return Order.of(isbn, null, null, quantity, OrderStatus.REJECTED);
    }

    public Flux<Order> consumeOrderDispatchedEvent(Flux<OrderDispatchedMessage> flux) {
        return flux.flatMap(message ->
                        orderRepository.findById(message.orderId()))
                .map(this::buildAcceptedOrder)
                .flatMap(orderRepository::save);
    }

    private void publishOrderAcceptedEvent(Order order) {
        if (order.status() != OrderStatus.ACCEPTED) {
            return;
        }

        var orderAcceptedMessage = new OrderAcceptedMessage(order.id());

        logger.info("Sending order accepted event with id {}", order.id());

        var result = streamBridge.send("acceptedOrder-out-0", orderAcceptedMessage);

        logger.info("Result of sending data for order with id {}: {}", order.id(), result);
    }

    private Order buildAcceptedOrder(Order existingOrder) {
        return new Order(
                existingOrder.id(),
                existingOrder.bookIsbn(),
                existingOrder.bookName(),
                existingOrder.bookPrice(),
                existingOrder.quantity(),
                OrderStatus.DISPATCHED,
                existingOrder.createdAt(),
                existingOrder.lastModifiedAt(),
                existingOrder.createdBy(),
                existingOrder.lastModifiedBy(),
                existingOrder.version());
    }
}