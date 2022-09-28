package com.bcg.humana.gatewaypoc.proxies;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {
  private Long id;
  private String firstName;
  private String lastName;
  private String address;
}
