package com.bcg.humana.gatewaypoc.proxies;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Date;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
  private Long id;
  private Long customerId;
  private Boolean referral;
  private String pharmacyNPI;
  private Date createdDate;
  private Date shippedDate;
  private Date deliveryDate;
  private Date cancelDate;
  private Date doNotShipUntilDate;
  private Integer itemQuantity;
  private Boolean orderConsented;
  private String orderType;
  private String source;
  private Boolean eligibleForOnline;
  private Boolean signatureRequired;
  private String status;
  private String currentQueue;
  private String billing;
  private String shipping;
  private Item[] items;
  private String displayLabel;
  private String helperText;
  private String orderStatusText;
  private String exceptions;
  private Date estimatedDate;
  private Boolean oldShipment;
}
