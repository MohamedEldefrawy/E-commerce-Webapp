package com.vodafone.repository.orderItems;

import com.vodafone.model.OrderItem;
import org.springframework.data.repository.CrudRepository;


public interface IOrderItemsRepository extends CrudRepository<OrderItem,Long> {
}
