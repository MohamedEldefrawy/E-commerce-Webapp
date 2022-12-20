package com.vodafone.repository.orderItems;

import com.vodafone.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IOrderItemsRepository extends JpaRepository<OrderItem,Long> {
}
