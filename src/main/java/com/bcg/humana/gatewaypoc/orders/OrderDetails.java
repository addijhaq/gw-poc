package com.bcg.humana.gatewaypoc.orders;

import com.bcg.humana.gatewaypoc.proxies.Customer;
import com.bcg.humana.gatewaypoc.proxies.Order;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import reactor.util.function.Tuple2;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDetails {
    private Order[] orders;
    private Customer customer;
    public OrderDetails() {
    }

    public OrderDetails(Order[] orders, Customer customer) {
      this.orders = orders;
      this.customer = customer;
    }
    public static OrderDetails makeOrderDetails(Tuple2<Order[], Customer> info) {
      return new OrderDetails(info.getT1(), info.getT2());
    }
}
