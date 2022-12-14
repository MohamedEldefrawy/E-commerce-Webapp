package com.vodafone.service;

import com.vodafone.model.Order;
import com.vodafone.repository.order.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService{
    private OrderRepository orderRepository;

    public List<Order> getAll(){
        return orderRepository.getAll().get();
    }
    public Order get(Long orderId){
        return orderRepository.getById(orderId);
    }
    public boolean create(Order order){
        return orderRepository.create(order);
    }
    public boolean update(Long orderId,Order order){
        return orderRepository.update(orderId,order);
    }
    public boolean delete(Long orderId){
        return orderRepository.delete(orderId);
    }
    public List<Order> getByCustomerId(Long customerId) { return orderRepository.getByCustomerId(customerId); }

}
