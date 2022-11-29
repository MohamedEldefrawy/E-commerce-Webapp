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
    public String addItem(CartItem item){
        return cartService.addItem(item);
    }

    public String removeItem(CartItem item){
        return cartService.removeItem(item);
    }

    public String getCartByCustomerId(Long id){
        return cartService.getCartByCustomerId(id);
    }

    public String clearCart(){
        return cartService.clearCart();
    }

    public String submitFinalOrder(){
        return cartService.submitFinalOrder();
    }
}
