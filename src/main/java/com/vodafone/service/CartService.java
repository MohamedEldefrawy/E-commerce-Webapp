package com.vodafone.service;

import com.vodafone.model.Cart;
import com.vodafone.model.CartItem;
import com.vodafone.model.Order;
import com.vodafone.model.OrderItem;
import com.vodafone.repository.cart.ICartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class CartService {
    private ICartRepository cartRepository;


    public boolean create(Cart entity) {
        return cartRepository.create(entity);
    }


    public boolean update(Long id, Cart updatedEntity) {
        return cartRepository.update(id, updatedEntity);
    }


    public boolean delete(Long id) {
        return cartRepository.delete(id);
    }


    public Cart get(Long id) {
        return cartRepository.getById(id);
    }


    public List<Cart> getAll() {
        return cartRepository.getAll();
    }


    public boolean removeItem(Long cartId, Long itemId) {
        return cartRepository.removeItem(cartId, itemId);
    }


    public Order submitFinalOrder(Long cartId) {
        Order order = showFinalOrder(cartId); //calculate total and transform CartItem to OrderItem
        clearCart(cartId); //submit and clear the cart
        return order;
    }

    public Order showFinalOrder(Long cartId) {
        Set<OrderItem> orderItems = new HashSet<>();
        Cart cart = get(cartId);
        Order order = new Order();
        order.setCustomer(cart.getCustomer());
        order.setDate(Date.valueOf(LocalDate.now()));
        //iterate over each cart item to transform it to order item.
        float total = 0f;
        for (CartItem cartItem : cart.getItems()) {
            if(cartItem.getQuantity()>0) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                //check available quantity in stock
                int quantity = cartItem.getQuantity();
                int availableInStock = cartItem.getProduct().getInStock();
                if (quantity <= availableInStock) {
                    //set product in Order
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setQuantity(quantity);
                    total += (float) (orderItem.getProduct().getPrice() * quantity);
                    //decrement product inStock variable
                    cartItem.getProduct().setInStock(availableInStock - quantity);
                    //add order to OrderItems
                    orderItems.add(orderItem);
                } else {
                    System.out.println("Product hasn't enough instances in stock");
                    break;
                }
            }
        }
        order.setTotal(total);
        order.setOrderItems(orderItems);
        return order;
    }

    public boolean clearCart(Long cartId) {
        return cartRepository.clearCart(cartId);
    }

    public int addItem(Long cartId, CartItem item) {
        return cartRepository.addItem(cartId, item);
    }

    public List<CartItem> getCartItems(Long cartId) {
        return cartRepository.getCartItems(cartId);
    }

    public int setProductQuantity(Long cartId, Long itemId, int newQuantity) {
        return cartRepository.setProductQuantity(cartId, itemId, newQuantity);
    }

    public int incrementProductQuantity(Long cartId, Long productId, int quantity) {
        return cartRepository.incrementProductQuantity(cartId, productId, quantity);
    }

    public int decrementProductQuantity(Long cartId, Long productId) {
        return cartRepository.decrementProductQuantity(cartId, productId);
    }
}
