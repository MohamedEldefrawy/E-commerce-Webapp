package com.vodafone.controller;

import com.vodafone.model.Order;
import com.vodafone.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("order")
public class OrderController {
    OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> findAll(int customerId){
        return orderService.findAll(customerId);
    }

    @GetMapping
    public Order findOne(@RequestParam("id") int orderId){
        return orderService.findOne(orderId);
    }

    @PutMapping
    public boolean save(Order order){
        return orderService.save(order);
    }

    @PostMapping
    public boolean updateOne(int orderId,Order order){
        return orderService.updateOne(orderId,order);
    }

    @DeleteMapping
    public boolean deleteOne(int orderId){
        return orderService.deleteOne(orderId);
    }
}
