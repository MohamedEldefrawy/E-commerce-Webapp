package com.vodafone.controller;

import com.vodafone.model.Order;
import com.vodafone.service.OrderService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class OrderController {
    OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    public List<Order> findAll(int customerId){
        return orderService.findAll(customerId);
    }
    public Order findOne(int orderId){
        return orderService.findOne(orderId);
    }
    public boolean save(Order order){
        return orderService.save(order);
    }
    public boolean updateOne(int orderId,Order order){
        return orderService.updateOne(orderId,order);
    }
    public boolean deleteOne(int orderId){
        return orderService.deleteOne(orderId);
    }
}
