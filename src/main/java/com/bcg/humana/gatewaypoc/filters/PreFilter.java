package com.bcg.humana.gatewaypoc.filters;

import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class PreFilter implements GlobalFilter {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    log.info("Pre-Filter executed");

    String requestPath = exchange.getRequest().getPath().toString();
    log.info("Request path = " + requestPath);

    HttpHeaders headers = exchange.getRequest().getHeaders();
    Set<String> headerNames = headers.keySet();

    headerNames.forEach((header) -> {
      log.info(header + " " + headers.get(header));
    });

    return chain.filter(exchange);
  }

}
