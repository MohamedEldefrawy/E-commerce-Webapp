package com.vodafone.service;

import com.vodafone.exception.NullIdException;
import com.vodafone.exception.NullOrderException;
import com.vodafone.model.Order;
import com.vodafone.repository.order.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService{
    private OrderRepository orderRepository;

    public List<Order> getAll(){
        return orderRepository.getAll().get();
    }
    public Order get(Long orderId){

        return orderRepository.getById(orderId).get();
    }
    public boolean create(Order order){
        if(order ==null){
            throw new NullOrderException("Null Order Provided");
        }
        else if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()){
            return orderRepository.create(order).isPresent();
        }else {
            return false;
        }
    }
    public boolean update(Long orderId,Order order){
        if (order == null)
            throw  new NullOrderException("Your updated Order is null and that's not provided");
        if (orderId == null )
            throw new NullIdException("Null order id is provided");
        return orderRepository.update(orderId,order);
    }
    public boolean delete(Long orderId){
        return orderRepository.delete(orderId);
    }
    public List<Order> getByCustomerId(Long customerId) { return orderRepository.getByCustomerId(customerId); }

}
