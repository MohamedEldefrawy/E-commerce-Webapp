package com.vodafone.service;

import com.vodafone.model.Order;
import com.vodafone.repository.order.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService{
    OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findAll(int customerId){
        return orderRepository.findAll(customerId);
    }
    public Order findOne(int orderId){
        return orderRepository.findOne(orderId);
    }
    public boolean save(Order order){
        return orderRepository.save(order);
    }
    public boolean updateOne(int orderId,Order order){
        return orderRepository.updateOne(orderId,order);
    }
    public boolean deleteOne(int orderId){
        return orderRepository.deleteOne(orderId);
    }
}
