package com.vodafone.controller.rest;

import com.vodafone.model.Order;
import com.vodafone.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;
    @GetMapping
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("{id}")
    public ResponseEntity<Order> getOrderByID(@PathVariable Long id){
        return ResponseEntity.ok(orderService.get(id));
    }

    @GetMapping("{id}")
    public List<Order> getOrdersByCustomerId(@PathVariable Long id){
        return orderService.getByCustomerId(id);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @PutMapping("{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id,@RequestBody Order order){
        return ResponseEntity.ok(orderService.updateOrder(id,order));
    }
    @DeleteMapping ("{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }









}
