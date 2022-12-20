package com.vodafone.service;

import com.vodafone.exception.NullIdException;
import com.vodafone.exception.NullOrderException;
import com.vodafone.exception.customer.CustomerNotFoundException;
import com.vodafone.exception.order.OrderFailedCreationException;
import com.vodafone.exception.order.OrderNotFoundException;
import com.vodafone.model.Order;
import com.vodafone.repository.order.IOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class OrderService{
    private IOrderRepository orderRepository;

    public List<Order> getAllOrders(){
        return (List<Order>) orderRepository.findAll();
    }
    public Order get(Long orderId){
        return orderRepository.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException("Couldn't find order "+orderId));
    }
    public Order createOrder(Order order){
        if(order ==null){
            throw new NullOrderException("Null Order Provided");
        }
        else if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()){
            return orderRepository.save(order);
        }else {
            throw new OrderFailedCreationException("Failed to create this order");
        }
    }
    public Order updateOrder(Long orderId,Order order){
        if (order == null)
            throw  new NullOrderException("Your updated Order is null and that's not provided");
        if (orderId == null )
            throw new NullIdException("Null order id is provided");

        Order oldOrder = orderRepository.findById(orderId)
                .orElseThrow(()->new OrderNotFoundException("Couldn't find this order "+orderId));

        oldOrder.setOrderItems(order.getOrderItems());
        oldOrder.setCustomer(order.getCustomer());
        oldOrder.setDate(order.getDate());
        oldOrder.setTotal(order.getTotal());

        return orderRepository.save(oldOrder);
    }
    public void deleteOrder(Long orderId){
        if (orderId == null )
            throw new NullIdException("Null order id is provided");
        orderRepository.deleteById(orderId);
    }

    public List<Order> getByCustomerId(Long customerId) {
        if (customerId == null )
            throw new NullIdException("Null Customer id is provided");
        return orderRepository.findAllByCustomerId(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("Couldn't find orders for this customer "+customerId));
    }

}
