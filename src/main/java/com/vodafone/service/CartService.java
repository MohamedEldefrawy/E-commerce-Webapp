package com.vodafone.service;

import com.vodafone.model.Cart;
import com.vodafone.model.CartItem;
import com.vodafone.model.Order;
import com.vodafone.repository.cart.ICartRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CartService {
    private ICartRepository cartRepository;


    public boolean create(Cart entity) {
        return cartRepository.create(entity);
    }


    public boolean update(Long id, Cart updatedEntity) {
        return cartRepository.update(id, updatedEntity);
    }


    public boolean delete(Long id) {
        return cartRepository.delete(id);
    }


    public Cart get(Long id) {
        return cartRepository.get(id);
    }


    public List<Cart> getAll() {
        return cartRepository.getAll();
    }


    public boolean removeItem(Long cartId, Long itemId) {
        return cartRepository.removeItem(cartId, itemId);
    }

    public boolean clearCart(Long cartId) {
        return cartRepository.clearCart(cartId);
    }

    public Order submitFinalOrder(Long cartId) {
        return cartRepository.submitFinalOrder(cartId);
    }

    public Order showFinalOrder(Long cartId) {
        return cartRepository.showFinalOrder(cartId);
    }

    public boolean addItem(Long cartId, CartItem item) {
        return cartRepository.addItem(cartId, item);
    }

    public List<CartItem> getCartItems(Long cartId) {
        return cartRepository.getCartItems(cartId);
    }

    public int setProductQuantity(Long cartId, Long itemId, int newQuantity){ return cartRepository.setProductQuantity(cartId, itemId, newQuantity); }

    public int incrementProductQuantity(Long cartId, Long productId,int quantity){ return cartRepository.incrementProductQuantity(cartId, productId, quantity); }

    public int decrementProductQuantity(Long cartId, Long productId){ return cartRepository.decrementProductQuantity(cartId, productId); }
}
