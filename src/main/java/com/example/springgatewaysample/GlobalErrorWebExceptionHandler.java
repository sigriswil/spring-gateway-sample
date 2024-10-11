package com.example.springgatewaysample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(-1)
@Component
public class GlobalErrorWebExceptionHandler implements ErrorWebExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalErrorWebExceptionHandler.class);

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        LOGGER.error("MDC: {}", MDC.getCopyOfContextMap());
        return Mono.error(ex);
    }
}
