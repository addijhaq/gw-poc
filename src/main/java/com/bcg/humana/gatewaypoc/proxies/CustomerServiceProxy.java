package com.bcg.humana.gatewaypoc.proxies;

import com.bcg.humana.gatewaypoc.orders.OrderDestinations;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceProxy {

  private final OrderDestinations destinations;
  private final WebClient client;

  public CustomerServiceProxy(OrderDestinations destinations, WebClient client) {
    this.destinations = destinations;
    this.client = client;
  }

  public Mono<Customer> findById(String customerId) {
    return client
        .get()
        .uri(destinations.getCustomerServiceUrl() + "/customers/{customerId}", customerId)
        .exchangeToMono(resp -> switch (resp.statusCode()) {
          case OK -> resp.bodyToMono(Customer.class);
          case NOT_FOUND -> Mono.error(new CustomerNotFoundException());
          default -> Mono.error(new RuntimeException("Unknown" + resp.statusCode()));
        });
  }
}
