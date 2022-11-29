package com.vodafone.repository.order;

import com.vodafone.model.Order;
import com.vodafone.repository.Repository;

import java.util.List;

public interface IOrderRepository extends Repository {

    public List<Order> findAll(int customerId);
    public Order findOne(int orderId);
    public boolean save(Order order);
    public boolean updateOne(int orderId,Order order);
    public boolean deleteOne(int orderId);
}
