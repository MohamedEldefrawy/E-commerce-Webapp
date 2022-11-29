package com.vodafone.repository.orderItems;

import com.vodafone.model.OrderItem;
import com.vodafone.repository.Repository;
import java.util.List;

public interface IOrderItemsRepository extends Repository {
    public boolean save(OrderItem orderItem);
    public List<OrderItem> findAll();
    public OrderItem findOne(int id);
    public boolean deleteOne(int id);
}
