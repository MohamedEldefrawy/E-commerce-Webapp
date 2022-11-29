package com.vodafone.repository.cart;

import com.vodafone.config.HibernateConfig;
import com.vodafone.model.Cart;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@NoArgsConstructor
@AllArgsConstructor
public class CartRepository implements ICartRepository {
    HibernateConfig config;

    @Override
    public String removeItem(Long cartId, Long itemId) {
        return null;
    }

    @Override
    public String clearCart(Long cartId) {
        return null;
    }

    @Override
    public String submitFinalOrder(Long cartId) {
        return null;
    }

    @Override
    public String addItem(Long cartId, CartItem item) {
        return null;
    }

    @Override
    public String getCartItems(Long cartId) {
        return null;
    }
}
