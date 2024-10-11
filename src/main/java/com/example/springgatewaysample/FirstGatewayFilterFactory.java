package com.example.springgatewaysample;

import io.micrometer.context.ContextRegistry;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.util.context.Context;

import java.util.UUID;

@Component
public class FirstGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirstGatewayFilterFactory.class);

    @PostConstruct
    void init() {
        ContextRegistry.getInstance().registerThreadLocalAccessor(
                "MDC",
                MDC::getCopyOfContextMap,
                MDC::setContextMap,
                MDC::clear);
    }

    @Override
    public GatewayFilter apply(Object config) {
        return new OrderedGatewayFilter((exchange, chain) -> {
            MDC.put("transactionId", UUID.randomUUID().toString());
            return chain.filter(exchange)
                    .doFinally(signalType -> LOGGER.info("FirstGatewayFilterFactory.doFinally, MDC: {}", MDC.getCopyOfContextMap()))
                    .contextWrite(Context.of("MDC", MDC.getCopyOfContextMap()));
        }, 10);
    }
}
