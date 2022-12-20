package com.vodafone.service;

import com.vodafone.exception.NullIdException;
import com.vodafone.exception.cart.NegativeQuantityException;
import com.vodafone.exception.cart.NullCartException;
import com.vodafone.exception.cart.NullCartItemException;
import com.vodafone.model.Cart;
import com.vodafone.model.CartItem;
import com.vodafone.model.Order;
import com.vodafone.model.OrderItem;
import com.vodafone.repository.cart.CartRepository;
import com.vodafone.repository.cart.ICartRepository;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CartService {
    private final ICartRepository cartRepository;
    private final Logger logger;

    public CartService(ICartRepository cartRepository) {
        this.cartRepository = cartRepository;
        this.logger = LoggerFactory.getLogger(CartRepository.class);
    }

    public Cart create(Cart entity) {
        if (entity == null)
            throw new NullCartException("Null cart provided");
        return cartRepository.save(entity);
    }


    public Cart update(Long id, Cart updatedEntity) {
        if (id == null)
            throw new NullIdException("Null cart id is provided");
        if (updatedEntity == null)
            throw new NullCartException("Null cart provided");
        updatedEntity.setId(id);
        return cartRepository.save(updatedEntity);
    }


    public boolean delete(Long id) {
        if (id == null)
            throw new NullIdException("Null cart id is provided");
        Optional<Cart> cart = cartRepository.findById(id);
        if (!cart.isPresent())
            throw new HibernateException("No cart exists with this id");
        cartRepository.delete(cart.get());
        return !cartRepository.findById(id).isPresent(); //if item is deleted then return true
    }


    public Cart get(Long id) {
        if (id == null)
            throw new NullIdException("Null cart id is provided");
        Optional<Cart> cart = cartRepository.findById(id);
        if (!cart.isPresent())
            throw new HibernateException("Cart not found with provided id");
        return cart.get();
    }


    public List<Cart> getAll() {
        return (List<Cart>) cartRepository.findAll();
    }


    public boolean removeItem(Long cartId, Long itemId) {
        if (cartId == null)
            throw new NullIdException("Null cart id is provided");
        if (itemId == null)
            throw new NullIdException("Null item id is provided");
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (!cart.isPresent())
            throw new HibernateException("No cart exists with this id");
        return cartRepository.removeItem(cart.get(), itemId);
    }


    public Order submitFinalOrder(Long cartId) {
        if (cartId == null)
            throw new NullIdException("Null cart id is provided");
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (!cart.isPresent())
            throw new HibernateException("Cart not found with provided id");
        Order order = showFinalOrder(cartId); //calculate total and transform CartItem to OrderItem
        cartRepository.clearCart(cart.get()); //submit and clear the cart
        return order;
    }

    public Order showFinalOrder(Long cartId) {
        if (cartId == null)
            throw new NullIdException("Null id is provided");
        Set<OrderItem> orderItems = new HashSet<>();
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (!cart.isPresent())
            throw new HibernateException("Cart not found with provided id");
        Order order = new Order();
        order.setCustomer(cart.get().getCustomer());
        order.setDate(Date.valueOf(LocalDate.now()));
        //iterate over each cart item to transform it to order item.
        float total = 0f;
        for (CartItem cartItem : cart.get().getItems()) {
            if (cartItem.getQuantity() > 0) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                //check available quantity in stock
                int quantity = cartItem.getQuantity();
                int availableInStock = cartItem.getProduct().getInStock();
                if (quantity <= availableInStock) {
                    //set product in Order
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setQuantity(quantity);
                    total += (float) (orderItem.getProduct().getPrice() * quantity);
                    //decrement product inStock variable
                    cartItem.getProduct().setInStock(availableInStock - quantity);
                    //add order to OrderItems
                    orderItems.add(orderItem);
                } else {
                    logger.error("Product hasn't enough instances in stock");
                    break;
                }
            }
        }
        order.setTotal(total);
        order.setOrderItems(orderItems);
        return order;
    }

    public List<CartItem> getCartItems(Long cartId) {
        if (cartId == null)
            throw new NullIdException("Null cart id is provided");
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (!cart.isPresent())
            throw new HibernateException("Cart not found with provided id");
        return cart.get().getItems();
    }

    public boolean clearCart(Long cartId) {
        if (cartId == null) {
            throw new NullIdException("Null cart id is provided");
        }
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (!cart.isPresent())
            throw new HibernateException("Cart not found with provided id");
        return cartRepository.clearCart(cart.get());
    }

    public int addItem(Long cartId, CartItem item) {
        if (cartId == null)
            throw new NullIdException("Null cart id is provided");
        if (item == null)
            throw new NullCartItemException("Null cart item is provided");
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (!cart.isPresent())
            throw new HibernateException("Cart not found with provided id");
        return cartRepository.addItem(cart.get(), item);
    }

    public int setProductQuantity(Long cartId, Long itemId, int newQuantity) {
        if (cartId == null)
            throw new NullIdException("Null cart id is provided");
        if (itemId == null)
            throw new NullIdException("Null cart item is provided");
        if (newQuantity < 0)
            throw new NegativeQuantityException("Negative quantity provided");
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (!cart.isPresent())
            throw new HibernateException("Cart not found with provided id");
        return cartRepository.setProductQuantity(cart.get(), itemId, newQuantity);
    }

    public int incrementProductQuantity(Long cartId, Long itemId, int quantity) {
        if (cartId == null)
            throw new NullIdException("Null cart id is provided");
        if (itemId == null)
            throw new NullIdException("Null cart item is provided");
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (!cart.isPresent())
            throw new HibernateException("Cart not found with provided id");
        return cartRepository.incrementProductQuantity(cart.get(), itemId, quantity);
    }

    public int decrementProductQuantity(Long cartId, Long itemId, int quantity) {
        if (cartId == null)
            throw new NullIdException("Null cart id is provided");
        if (itemId == null)
            throw new NullIdException("Null cart item is provided");
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (!cart.isPresent())
            throw new HibernateException("Cart not found with provided id");
        return cartRepository.decrementProductQuantity(cart.get(), itemId, quantity);
    }
}

//Todo: refactor hibernate exceptions