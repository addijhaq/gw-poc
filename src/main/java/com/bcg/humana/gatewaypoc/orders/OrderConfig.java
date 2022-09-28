package com.bcg.humana.gatewaypoc.orders;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

//import com.bcg.humana.gatewaypoc.proxies.OrderServiceProxy;
import com.bcg.humana.gatewaypoc.proxies.CustomerServiceProxy;
import com.bcg.humana.gatewaypoc.proxies.OrderServiceProxy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class OrderConfig {
  @Bean
  public RouteLocator orderProxyRouting(RouteLocatorBuilder builder, OrderDestinations destination) {
    return builder.routes()
        .route(r -> r.path("/orders/orderId/*").uri(destination.getOrderServiceUrl()))
        .build();
  }
  @Bean
  public RouterFunction<ServerResponse> orderByIdHandlerRouting(OrderHandlers orderHandlers) {
    return RouterFunctions.route(GET("/orders/customerOrders/{customerId}"), orderHandlers::getCustomerOrders);
  }

  @Bean
  public OrderHandlers orderHandlers(OrderServiceProxy orderService, CustomerServiceProxy customerService) {
    return new OrderHandlers(orderService, customerService);
  }

  @Bean
  public WebClient webClient() {
    return WebClient.create();
  }
}
