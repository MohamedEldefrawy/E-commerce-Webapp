package com.vodafone.repository.cart;

import com.vodafone.model.Cart;
import com.vodafone.model.CartItem;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public abstract class CartRepository implements ICartRepository {
    private final Logger logger = LoggerFactory.getLogger(CartRepository.class);

    @Override
    public boolean removeItem(Cart cart, Long itemId) {
        cart.getItems().removeIf(item -> Objects.equals(item.getId(), itemId)); //remove desired item
        cart = save(cart);
        //todo: test below line to return true if item is not found anymore
        return cart.getItems().stream().noneMatch(cartItem -> cartItem.getId().equals(itemId));
    }

    @Override
    public boolean clearCart(Cart cart) {
        //todo: check if items is deleted from db or not
        cart.getItems().clear();
        cart = save(cart);
        return cart.getItems().size() == 0;
    }

    /***
     *
     * @param cart Cart object to add item to it
     * @param item CartItem to be added to cart
     * @return the quantity of item added
     * returns 0 in case nit available in stock (0 quantity added)
     * returns -1 in case of exception
     */
    @Override
    public int addItem(Cart cart, CartItem item) { //0 -> not added -1 -> exception otherwise--> added
        List<CartItem> items = cart.getItems();
        //checks if item is already in cart then increments quantity
        List<CartItem> matchingProduct = items.stream()
                .filter(i -> i.getProduct().getId().equals(item.getProduct().getId()))
                .collect(Collectors.toList());
        //if product already in cart then add to quantity
        if (!matchingProduct.isEmpty()) {
            return incrementProductQuantity(cart, item.getProduct().getId(), item.getQuantity());
        } else {
            if (item.getProduct().getInStock() >= item.getQuantity()) {
                items.add(item); //add item to cart list
                cart = save(cart); //update cart
            } else {
                return 0;
            }
        }
        return item.getQuantity();
    }

    @Override
    public int setProductQuantity(Cart cart, Long itemId, int newQuantity) {
        for (CartItem item : cart.getItems()) {
            if (item.getId().equals(itemId))
                item.setQuantity(newQuantity);
        }
        save(cart); //update cart
        return newQuantity;
    }

    @Override
    public int incrementProductQuantity(Cart cart, Long productId, int quantity) {
        final int[] newQuantity = {0};
        cart.getItems().stream().filter(item -> item.getId().equals(productId)).forEach(item -> {
            newQuantity[0] = item.getQuantity() + quantity;
            if (item.getProduct().getInStock() >= newQuantity[0])
                item.setQuantity(newQuantity[0]);
        });
        save(cart); //update cart
        return newQuantity[0];
    }

    @Override
    public int decrementProductQuantity(Cart cart, Long productId, int quantity) {
        final int[] newQuantity = {0};
        cart.getItems().stream().filter(item -> item.getId().equals(productId)).forEach(item -> {
            newQuantity[0] = item.getQuantity() + quantity;
            if (newQuantity[0] >= 0)
                item.setQuantity(newQuantity[0]);
        });
        save(cart); //update cart
        return newQuantity[0];
    }
}
