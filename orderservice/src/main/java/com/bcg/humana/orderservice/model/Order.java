package com.bcg.humana.orderservice.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

enum OrderPlatform {
  PLATFORM_SPEC_R("SPEC_R"),
  PLATFORM_SPEC_O("SPEC_O"),
  PLATFORM_TRAD("Trad"),
  PLATFORM_SPEC("Spec"),
  PLATFORM_SPEC10("SPEC10"),
  PLATFORM_SPEC20("SPEC20"),
  PLATFORM_SPEC30("SPEC30"),
  SPEC_NPI("1942441886"),
  GEN_NPI("1710927462");

  OrderPlatform(String spec) {
  }
}

enum OrderStatus {
  OPEN("O"),
  CANCELLED("C"),
  HOLD("H"),
  SHIPPED("S");
  OrderStatus(String status) {
  }
}

enum OrderType {
  OTC("OTC"),
  HWOTC("HWOTC"),
  NEWRX("NEWRX"),
  LOWERCASE_NEWRX("NewRx"),
  RENEWRX ("RENEWRX");

  OrderType(String otc) {
  }
}
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "orders")
public class Order implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Long id;
  private Long customerId;
  private Boolean referral;
  @Column(name = "pharmacy_npi")
  private String pharmacyNPI;
  private Date createdDate;
  private Date shippedDate;
  private Date deliveryDate;
  private Date cancelDate;
  private Date doNotShipUntilDate;
  private Integer itemQuantity;
  private Boolean orderConsented;
  private OrderType orderType;
  private String source;
  private Boolean eligibleForOnline;
  private Boolean signatureRequired;
  private OrderStatus status;
  private String currentQueue;
  private String billing;
  private String shipping;
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "orders_items",
      joinColumns = {
          @JoinColumn(name = "order_id")
      },
      inverseJoinColumns = {
          @JoinColumn(name = "item_id")
      }
  )
  Set <Item> items = new HashSet<Item>();
  private String displayLabel;
  private String helperText;
  private String orderStatusText;
  private String exceptions;
  private Date estimatedDate;
  private Boolean oldShipment;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Order order = (Order) o;
    return id != null && Objects.equals(id, order.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
