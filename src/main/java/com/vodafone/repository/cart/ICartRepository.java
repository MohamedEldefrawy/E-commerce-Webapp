package com.vodafone.repository.cart;

public interface ICartRepository {
    String removeItem(CartItem item);

    String getCartByCustomerId(Long id);

    String clearCart();

    String submitFinalOrder();

    String addItem(CartItem item);

    String getCartItem();
}
