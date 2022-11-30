package com.vodafone.service;

import com.vodafone.model.CartItem;
import com.vodafone.model.Order;
import com.vodafone.repository.cart.ICartRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class CartService {
    ICartRepository cartRepository;
    public boolean removeItem(Long cartId, Long itemId) {
       return cartRepository.removeItem(cartId,itemId);
    }

    public boolean clearCart(Long cartId) {
        return cartRepository.clearCart(cartId);
    }

    public Order submitFinalOrder(Long cartId) {
        return cartRepository.submitFinalOrder(cartId);
    }

    public boolean addItem(Long cartId, CartItem item) {
        return cartRepository.addItem(cartId,item);
    }

    public List<CartItem> getCartItems(Long cartId) {
        return cartRepository.getCartItems(cartId);
    }
}
