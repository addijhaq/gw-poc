package com.bcg.humana.gatewaypoc.proxies;

import com.bcg.humana.gatewaypoc.orders.OrderDestinations;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OrderServiceProxy {

  private final OrderDestinations orderDestinations;

  private final WebClient client;

  public OrderServiceProxy(OrderDestinations orderDestinations, WebClient client) {
    this.orderDestinations = orderDestinations;
    this.client = client;
  }

  public Mono<Order> findById(String orderId) {
    return client.get()
        .uri(orderDestinations.getOrderServiceUrl() + "/orders/orderId/{orderId}", orderId)
        .exchangeToMono(resp -> switch(resp.statusCode()) {
          case OK -> resp.bodyToMono(Order.class);
          case NOT_FOUND -> Mono.error(new OrderNotFoundException());
          default -> Mono.error(new RuntimeException("Unknown" + resp.statusCode()));
        });
  }
  public Mono<Order[]> findOrdersByCustomerId(String customerId) {
    return client
        .get()
        .uri(orderDestinations.getOrderServiceUrl() + "/orders/customerId/{customerId}", customerId)
        .exchangeToMono(resp -> switch (resp.statusCode()) {
          case OK -> resp.bodyToMono(Order[].class);
          case NOT_FOUND -> Mono.error(new OrderNotFoundException());
          default -> Mono.error(new RuntimeException("Unknown" + resp.statusCode()));
        });
  }
}
