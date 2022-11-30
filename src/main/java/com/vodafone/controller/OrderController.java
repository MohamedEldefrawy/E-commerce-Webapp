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
    public List<Order> getAll(){
        return orderService.getAll();
    }

    @GetMapping
    public Order get(@RequestParam("id") Long orderId){
        return orderService.get(orderId);
    }

    @PutMapping
    public boolean create(Order order){
        return orderService.create(order);
    }

    @PostMapping
    public boolean updateOne(Long orderId,Order order){
        return orderService.update(orderId,order);
    }

    @DeleteMapping
    public boolean delete(Long orderId){
        return orderService.delete(orderId);
    }
}
