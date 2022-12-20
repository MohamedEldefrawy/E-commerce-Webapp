package com.vodafone.repository.cart;

import com.vodafone.model.Cart;
import com.vodafone.model.CartItem;
import org.springframework.data.repository.CrudRepository;

public interface ICartRepository extends CrudRepository<Cart,Long> {
    boolean removeItem(Cart cart, Long itemId);

    boolean clearCart(Cart cart);

    int addItem(Cart cart, CartItem item);

    int setProductQuantity(Cart cart, Long itemId, int newQuantity);

    int incrementProductQuantity(Cart cart, Long productId, int quantity);

    int decrementProductQuantity(Cart cart, Long productId, int quantity);
}
