package org.mall.dispatcherservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Configuration
public class DispatchingFunctions {
    private static final Logger logger = LoggerFactory.getLogger(DispatchingFunctions.class);

    @Bean
    public Function<OrderAcceptedMessage, Long> pack() {
        return acceptedOrder -> {
            logger.info("The order with id {} is packed", acceptedOrder.orderId());
            return acceptedOrder.orderId();
        };
    }

    @Bean
    public Function<Flux<Long>, Flux<OrderDispatchedMessage>> label() {
        return order -> order.map(orderId -> {
            logger.info("The order with id {} is labeled", orderId);
            return new OrderDispatchedMessage(orderId);
        });
    }
}