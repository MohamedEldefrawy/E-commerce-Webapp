package com.vodafone.repository.cart;

import com.vodafone.model.Cart;
import com.vodafone.model.CartItem;
import com.vodafone.model.Order;
import com.vodafone.repository.Repository;

import java.util.List;

public interface ICartRepository extends Repository<Long,Cart> {
    boolean removeItem(Long cartId, Long itemId);

    boolean clearCart(Long cartId);

    int addItem(Long cartId, CartItem item);

    List<CartItem> getCartItems(Long cartId);

    int setProductQuantity(Long cartId, Long itemId, int newQuantity);

    int incrementProductQuantity(Long cartId, Long productId,int quantity);

    int decrementProductQuantity(Long cartId, Long productId);
}
