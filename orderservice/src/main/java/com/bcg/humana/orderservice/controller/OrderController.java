package com.bcg.humana.orderservice.controller;

import com.bcg.humana.orderservice.model.Order;
import com.bcg.humana.orderservice.respository.OrderRepo;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {

  private final OrderRepo orderRepository;

  public OrderController(OrderRepo orderRepository) {
    this.orderRepository = orderRepository;
  }

  @RequestMapping(path = "/healthCheck", method = RequestMethod.GET)
  public ResponseEntity<Object> healthCheck() {
    return new ResponseEntity<>("UP", HttpStatus.OK);
  }

  @RequestMapping(path = "/customerId/{customerId}", method = RequestMethod.GET)
  public ResponseEntity<Object> getOrderByCustomerId(@PathVariable long customerId) {
    try {
      List<Order> orders = orderRepository.findByCustomerId(customerId);
      return orders.size() == 0 ? new ResponseEntity<>("No orders found", HttpStatus.NOT_FOUND)
          : new ResponseEntity<>(orders, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("Cannot process request", HttpStatus.NOT_FOUND);
    }
  }

  @RequestMapping(path = "/orderId/{orderId}", method = RequestMethod.GET)
  public ResponseEntity<Object> getOrderById(@PathVariable long orderId) {
    try {
      return new ResponseEntity<>(Collections.singletonList(
          orderRepository.findById(orderId)
              .orElseThrow(() -> new Exception("Cannot find order with id " + orderId))),
          HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("Cannot process request", HttpStatus.NOT_FOUND);
    }
  }
}
