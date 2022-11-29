package com.vodafone.repository.cart;

public interface ICartRepository {
    String removeItem(Long cartId, Long itemId);

    String clearCart(Long cartId);

    String submitFinalOrder(Long cartId);

    String addItem(Long cartId, CartItem item);

    String getCartItems(Long cartId);
}
