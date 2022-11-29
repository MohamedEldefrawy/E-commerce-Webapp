package com.vodafone.controller;

import com.vodafone.service.CartService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/carts")
public class CartController {
    CartService cartService;

    //todo: add mappings
    public String addItem(Long cartId, CartItem item) {
        return cartService.addItem(cartId,item);
    }

    public String removeItem(Long cartId, Long itemId) {
        return cartService.removeItem(cartId,itemId);
    }

    public String getCartByCustomerId(Long id) {
        return cartService.getCartByCustomerId(id);
    }

    public String clearCart(Long cartId) {
        return cartService.clearCart(cartId);
    }

    public String submitFinalOrder(Long cartId) {
        return cartService.submitFinalOrder(cartId);
    }

    public String getCartItems(Long cartId) {
        return cartService.getCartItems(cartId);
    }
}
