package com.vodafone.controller;

import com.vodafone.model.Cart;
import com.vodafone.model.CartItem;
import com.vodafone.service.CartService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/carts")
public class CartController {
    CartService cartService;

    @PostMapping("{cartId}")
    public String addItem(@PathVariable Long cartId, @RequestBody CartItem item) {
        cartService.addItem(cartId, item);
        return null;
    }

    @PutMapping("{cartId}/{itemId}")
    public String removeItem(@PathVariable Long cartId, @PathVariable Long itemId) {
        cartService.removeItem(cartId, itemId);
        return null;
    }

    @PutMapping("{cartId}/clear")
    public String clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return null;
    }

    @PostMapping("{cartId}/submit")
    public String submitFinalOrder(@PathVariable Long cartId) {
        cartService.submitFinalOrder(cartId);
        return null;
    }

    @GetMapping("{cartId}/order")
    public String showFinalOrder(@PathVariable Long cartId) {
        cartService.showFinalOrder(cartId);
        return null;
    }

    @GetMapping("{cartId}/items")
    public String getCartItems(@PathVariable Long cartId) {
        cartService.getCartItems(cartId);
        return null;
    }

    @PostMapping
    public String create(@RequestBody Cart entity) {
        cartService.create(entity);
        return null;
    }

    @PutMapping("{id}")
    public String update(@PathVariable Long id, @RequestBody Cart updatedEntity) {
        cartService.update(id, updatedEntity);
        return null;
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id) {
        cartService.delete(id);
        return null;
    }

    @GetMapping("{id}")
    public String get(@PathVariable Long id) {
        cartService.get(id);
        return null;
    }

    @GetMapping
    public String getAll() {

        cartService.getAll();
        return null;
    }

    @PutMapping("{cartId}/{itemId}/{quantity}")
    public String setProductQuantity(@PathVariable Long cartId, @PathVariable Long itemId, @PathVariable int quantity) {
        cartService.setProductQuantity(cartId, itemId, quantity);
        return null;
    }

    @PutMapping("{cartId}/{itemId}/increment")
    public String incrementProductQuantity(@PathVariable Long cartId, @PathVariable Long productId) {
        cartService.incrementProductQuantity(cartId, productId,1);
        return null;
    }

    @PutMapping("{cartId}/{itemId}/decrement")
    public String decrementProductQuantity(@PathVariable Long cartId, @PathVariable Long itemId) {
        cartService.decrementProductQuantity(cartId, itemId);
        return null;
    }
}
