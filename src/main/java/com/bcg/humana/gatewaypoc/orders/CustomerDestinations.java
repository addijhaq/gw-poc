package com.bcg.humana.gatewaypoc.orders;

import javax.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "order.destinations")
public class CustomerDestinations {
  @NotNull
  private String customerServiceUrl;

  public String getCustomerServiceUrl() {
    return customerServiceUrl;
  }

  public void setCustomerServiceUrl(String customerServiceUrl) {
    this.customerServiceUrl = customerServiceUrl;
  }
}
