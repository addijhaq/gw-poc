package com.bcg.humana.orderservice.respository;

import com.bcg.humana.orderservice.model.Order;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepo extends CrudRepository<Order, Long> {
  List<Order> findByCustomerId(Long customerId);
}
