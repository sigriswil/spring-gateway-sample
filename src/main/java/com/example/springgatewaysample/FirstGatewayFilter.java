package com.example.springgatewaysample;

import io.micrometer.context.ContextRegistry;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.UUID;

@Component
public class FirstGatewayFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirstGatewayFilter.class);

    @PostConstruct
    void init() {
        ContextRegistry.getInstance().registerThreadLocalAccessor(
                "MDC",
                MDC::getCopyOfContextMap,
                MDC::setContextMap,
                MDC::clear);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        MDC.put("transactionId", UUID.randomUUID().toString());
        LOGGER.info("transactionId created. MDC: {}", MDC.getCopyOfContextMap());
        return chain.filter(exchange)
                .doFinally(signalType -> LOGGER.info("doFinally, MDC: {}", MDC.getCopyOfContextMap()))
                .contextWrite(Context.of("MDC", MDC.getCopyOfContextMap()));
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
