package com.bcg.humana.customerservice.model;

import java.util.Date;
import lombok.Data;

@Data
public class CustomerResponse {
  private Customer customer;

  public CustomerResponse(Customer customer){
    this.customer = customer;
  }
}
