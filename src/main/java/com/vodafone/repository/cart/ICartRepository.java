package com.vodafone.repository.cart;

import com.vodafone.model.Cart;
import com.vodafone.model.CartItem;
import com.vodafone.model.Order;
import com.vodafone.repository.Repository;

import java.util.List;

public interface ICartRepository extends Repository<Cart> {
    boolean removeItem(Long cartId, Long itemId);

    boolean clearCart(Long cartId);

    Order submitFinalOrder(Long cartId);

    boolean addItem(Long cartId, CartItem item);

    List<CartItem> getCartItems(Long cartId);
}
