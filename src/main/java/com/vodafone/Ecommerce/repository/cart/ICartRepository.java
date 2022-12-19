//package com.vodafone.Ecommerce.repository.cart;
//
//import com.vodafone.Ecommerce.model.Cart;
//import com.vodafone.Ecommerce.model.CartItem;
//import com.vodafone.Ecommerce.repository.Repository;
//
//public interface ICartRepository extends Repository<Long, Cart> {
//    boolean removeItem(Cart cart, Long itemId);
//
//    boolean clearCart(Cart cart);
//
//    int addItem(Cart cart, CartItem item);
//
//    int setProductQuantity(Cart cart, Long itemId, int newQuantity);
//
//    int incrementProductQuantity(Cart cart, Long productId, int quantity);
//
//    int decrementProductQuantity(Cart cart, Long productId, int quantity);
//}
