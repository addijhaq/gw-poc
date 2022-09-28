package com.bcg.humana.gatewaypoc.proxies;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
  private Long id;
  private String productName;
  private Integer quantity;
}
