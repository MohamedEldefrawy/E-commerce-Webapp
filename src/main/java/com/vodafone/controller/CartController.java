package com.vodafone.controller;

import com.vodafone.model.CartItem;
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
        return null;
    }

    public String removeItem(Long cartId, Long itemId) {
        return null;
    }

    public String clearCart(Long cartId) {
        return null;
    }

    public String submitFinalOrder(Long cartId) {
        return null;
    }

    public String getCartItems(Long cartId) {
        return null;
    }
}
