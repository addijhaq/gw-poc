package com.bcg.humana.orderservice.model;

import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderResponse {
  private List<Order> orders;
  private Date createdDate;
  public OrderResponse(List<Order> orders) {
    this.orders = orders;
    this.createdDate = new Date();
  }
}
