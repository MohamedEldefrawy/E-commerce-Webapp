package com.vodafone.service;

import com.vodafone.repository.cart.ICartRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class CartService {
    ICartRepository cartRepository;
    public String removeItem(Long cartId, Long itemId) {
       return cartRepository.removeItem(cartId,itemId);
    }

    public String clearCart(Long cartId) {
        return cartRepository.clearCart(cartId);
    }

    public String submitFinalOrder(Long cartId) {
        return cartRepository.submitFinalOrder(cartId);
    }

    public String addItem(Long cartId, CartItem item) {
        return cartRepository.addItem(cartId,item);
    }

    public String getCartItems(Long cartId) {
        return cartRepository.getCartItems();
    }
}
