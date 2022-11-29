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
    public String removeItem(CartItem item) {
       return cartRepository.removeItem(item);
    }

    public String getCartByCustomerId(Long id) {
        return cartRepository.getCartByCustomerId(id);
    }

    public String clearCart() {
        return cartRepository.clearCart();
    }

    public String submitFinalOrder() {
        return cartRepository.submitFinalOrder();
    }

    public String addItem(CartItem item) {
        return cartRepository.addItem(item);
    }

    public String getCartItems() {
        return cartRepository.getCartItem();
    }
}
