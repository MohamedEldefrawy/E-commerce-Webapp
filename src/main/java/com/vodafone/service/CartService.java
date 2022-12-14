package com.vodafone.service;

import com.vodafone.exception.*;
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
        if (entity == null)
            throw new NullCartException("Null cart provided");
        return cartRepository.create(entity).isPresent();
    }


    public boolean update(Long id, Cart updatedEntity) {
//        if (updatedEntity == null || get(id) == null)
        if (id == null)
            throw new NullIdException("Null cart id is provided");
        if (updatedEntity == null)
            throw new NullCartException("Null cart provided");
        return cartRepository.update(id, updatedEntity);
    }


    public boolean delete(Long id) {
//        if (get(id) == null)
//          throw new NullPointerException("No cart exists with this id");
        if (id == null)
            throw new NullIdException("Null cart id is provided");
        return cartRepository.delete(id);
    }


    public Cart get(Long id) {
        if (id == null)
            throw new NullIdException("Null cart id is provided");
        return cartRepository.getById(id).get();
    }


    public List<Cart> getAll() {
        return cartRepository.getAll().get();
    }


    public boolean removeItem(Long cartId, Long itemId) {
//        if (get(cartId) == null)
//            throw new NullPointerException("No cart exists with this id");
        if (cartId == null)
            throw new NullIdException("Null cart id is provided");
        if (itemId == null)
            throw new NullIdException("Null item id is provided");
//        long matches = get(cartId).getItems().stream().filter(item -> item.getId().equals(itemId)).count();
//        if (matches == 0)
//            throw new NulLCartItemException("No item exists with this id");
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
            if (cartItem.getQuantity() > 0) {
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
//        if (get(cartId) == null)
//            throw new NullPointerException("Null cart id is provided");
        if (cartId == null)
            throw new NullIdException("Null cart id is provided");
        return cartRepository.clearCart(cartId);
    }

    public int addItem(Long cartId, CartItem item) {
//        if (cartId == null)
//            throw new NullPointerException("Null cart id is provided");
        if (cartId == null)
            throw new NullIdException("Null cart id is provided");
        if (item == null)
            throw new NullCartItemException("Null cart item is provided");
        return cartRepository.addItem(cartId, item);
    }

    public List<CartItem> getCartItems(Long cartId) {
        if (cartId == null)
            throw new NullIdException("Null cart id is provided");
        return cartRepository.getCartItems(cartId);
    }

    public int setProductQuantity(Long cartId, Long itemId, int newQuantity) {
        if (cartId == null)
            throw new NullIdException("Null cart id is provided");
        if (itemId == null)
            throw new NullIdException("Null cart item is provided");
        if (newQuantity < 0)
            throw new NegativeQuantityException("Negative quantity provided");
        return cartRepository.setProductQuantity(cartId, itemId, newQuantity);
    }

    public int incrementProductQuantity(Long cartId, Long itemId, int quantity) {
        if (cartId == null)
            throw new NullIdException("Null cart id is provided");
        if (itemId == null)
            throw new NullIdException("Null cart item is provided");
        return cartRepository.incrementProductQuantity(cartId, itemId, quantity);
    }

    public int decrementProductQuantity(Long cartId, Long itemId) {
        if (cartId == null)
            throw new NullIdException("Null cart id is provided");
        if (itemId == null)
            throw new NullIdException("Null cart item is provided");
        return cartRepository.decrementProductQuantity(cartId, itemId);
    }
}
