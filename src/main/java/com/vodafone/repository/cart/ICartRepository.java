package com.vodafone.repository.cart;

public interface ICartRepository {
    String removeItem(Long cartId, Long itemId);

    String getCartByCustomerId(Long id);

    String clearCart(Long cartId);

    String submitFinalOrder(Long cartId);

    String addItem(Long cartId, CartItem item);

    String getCartItem(Long cartId);
}
