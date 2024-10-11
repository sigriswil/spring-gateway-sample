package com.example.springgatewaysample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class SecondGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecondGatewayFilterFactory.class);

    @Override
    public GatewayFilter apply(Object config) {
        return new OrderedGatewayFilter((exchange, chain) -> {
            LOGGER.info("SecondGatewayFilterFactory start, MDC: {}", MDC.getCopyOfContextMap());
            throw new RuntimeException();
//            return chain.filter(exchange);
        }, 20);
    }
}
