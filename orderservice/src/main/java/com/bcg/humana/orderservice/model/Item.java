package com.bcg.humana.orderservice.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "items")
public class Item {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="item_id", nullable = false)
  private Long id;
  private String productName;
  private Integer quantity;
  @ManyToMany(mappedBy = "items", cascade = { CascadeType.ALL })
  @Transient
  private Set<Order> orders = new HashSet<Order>();
}
