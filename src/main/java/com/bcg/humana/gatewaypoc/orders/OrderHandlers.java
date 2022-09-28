package com.bcg.humana.gatewaypoc.orders;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import com.bcg.humana.gatewaypoc.proxies.Customer;
import com.bcg.humana.gatewaypoc.proxies.CustomerServiceProxy;
import com.bcg.humana.gatewaypoc.proxies.Order;
import com.bcg.humana.gatewaypoc.proxies.OrderNotFoundException;
import com.bcg.humana.gatewaypoc.proxies.OrderServiceProxy;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

public class OrderHandlers {

  private final OrderServiceProxy orderService;
  private final CustomerServiceProxy customerService;

  public OrderHandlers(OrderServiceProxy orderService, CustomerServiceProxy customerService) {
    this.orderService = orderService;
    this.customerService = customerService;
  }

  public Mono<ServerResponse> getCustomerOrders(ServerRequest serverRequest) {
    String customerId = serverRequest.pathVariable("customerId");
    Mono<Order[]> orders = orderService.findOrdersByCustomerId(customerId);
    Mono<Customer> customer = customerService.findById(customerId);
    Mono<Tuple2<Order[], Customer>> combined = Mono.zip(orders, customer);

    Mono<OrderDetails> orderDetails = combined.map(OrderDetails::makeOrderDetails);

    return orderDetails.flatMap(od -> ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(fromValue(od)))
        .onErrorResume(OrderNotFoundException.class, e -> ServerResponse.notFound().build());
  }
}
