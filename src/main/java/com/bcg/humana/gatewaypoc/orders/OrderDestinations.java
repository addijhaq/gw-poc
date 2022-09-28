package com.bcg.humana.gatewaypoc.orders;

import javax.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "order.destinations")
public class OrderDestinations {
    @NotNull
    private String orderServiceUrl;

    public String getOrderServiceUrl() {
      return orderServiceUrl;
    }

    public void setOrderServiceUrl(String orderServiceUrl) {
      this.orderServiceUrl = orderServiceUrl;
    }
}
