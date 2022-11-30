package com.vodafone.repository.order;

import com.vodafone.model.Order;
import com.vodafone.repository.Repository;

import java.util.List;

public interface IOrderRepository extends Repository<Order> {

    public List<Order> getByCustomerId(Long customerId);

}
