//package com.vodafone.Ecommerce.service;
//
//import com.vodafone.Ecommerce.exception.NullIdException;
//import com.vodafone.Ecommerce.model.Order;
//import com.vodafone.Ecommerce.repository.order.OrderRepository;
//import com.vodafone.Ecommerce.exception.NullOrderException;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//
//@Service
//@AllArgsConstructor
//public class OrderService{
//    private OrderRepository orderRepository;
//
//    public List<Order> getAll(){
//        return orderRepository.getAll().get();
//    }
//    public Order get(Long orderId){
//        if (orderId == null || !orderRepository.getById(orderId).isPresent() )
//            throw new NullIdException("Null order id is provided");
//        return orderRepository.getById(orderId).get();
//    }
//    public boolean create(Order order){
//        if(order ==null){
//            throw new NullOrderException("Null Order Provided");
//        }
//        else if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()){
//            return orderRepository.create(order).isPresent();
//        }else {
//            return false;
//        }
//    }
//    public boolean update(Long orderId,Order order){
//        if (order == null)
//            throw  new NullOrderException("Your updated Order is null and that's not provided");
//        if (orderId == null )
//            throw new NullIdException("Null order id is provided");
//        return orderRepository.update(orderId,order);
//    }
//    public boolean delete(Long orderId){
//        if (orderId == null )
//            throw new NullIdException("Null order id is provided");
//        return orderRepository.delete(orderId);
//    }
//    public List<Order> getByCustomerId(Long customerId) {
//        if (customerId == null )
//            throw new NullIdException("Null Customer id is provided");
//        return orderRepository.getByCustomerId(customerId);
//    }
//
//}
