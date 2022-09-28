package com.bcg.humana.orderservice.respository;

import com.bcg.humana.orderservice.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepo extends JpaRepository<Item, Long> {
}
