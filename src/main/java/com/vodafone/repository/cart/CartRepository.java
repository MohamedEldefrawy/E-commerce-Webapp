package com.vodafone.repository.cart;

import com.vodafone.config.HibernateConfig;
import com.vodafone.model.Cart;
import com.vodafone.model.CartItem;
import com.vodafone.model.Order;
import com.vodafone.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class CartRepository implements ICartRepository {
    HibernateConfig config;


    @Override
    public boolean create(Cart entity) {
        try (Session session = config.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Long id, Cart updatedEntity) {
        try (Session session = config.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            updatedEntity.setId(id);
            session.update(updatedEntity);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = config.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Cart cart = get(id);
            if (cart == null)
                return false;
            session.delete(cart);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Cart get(Long id) {
        Cart cart = null;
        try (Session session = config.getSessionFactory().openSession()) {
            cart = session.get(Cart.class, id);
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
        return cart;
    }

    @Override
    public List<Cart> getAll() {
        List<Cart> carts = null;
        try (Session session = config.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            carts = session.createQuery("SELECT DISTINCT cart From Cart cart", Cart.class).getResultList();
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            return carts;
        }
        return carts;
    }

    @Override
    public boolean removeItem(Long cartId, Long itemId) {
        try (Session session = config.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            //remove cartItem from cart arrayList
            Cart cart = get(cartId);
            cart.getItems().removeIf(item -> Objects.equals(item.getId(), itemId)); //remove desired item
            session.update(cart); //update cart
            //Remove cartItem from DB
            CartItem cartItem = session.get(CartItem.class,itemId);
            session.delete(cartItem);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean clearCart(Long cartId) {
        try (Session session = config.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Cart cart = get(cartId);
            //check if it's null so we can't operate
            if (cart == null)
                return false;
            cart.getItems().clear();
            session.delete(cart);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Order submitFinalOrder(Long cartId) {
        Order order = showFinalOrder(cartId); //calculate total and transform CartItem to OrderItem
        clearCart(cartId); //submit and clear the cart
        return order;
    }

    @Override
    public Order showFinalOrder(Long cartId) {
        Set<OrderItem> orderItems = new HashSet<>();
        Cart cart = get(cartId);
        Order order = new Order();
        order.setCustomer(cart.getCustomer());
        order.setDate(Date.valueOf(LocalDate.now()));
        //iterate over each cart item to transform it to order item.
        float total = 0f;
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            //check available quantity in stock
            int quantity = cartItem.getQuantity();
            int availableInStock = cartItem.getProduct().getInStock();
            if (quantity <= availableInStock) {
                //set product in Order
                orderItem.setQuantity(quantity);
                orderItem.setProduct(cartItem.getProduct());
                //add sub-total
                total += (float) (orderItem.getProduct().getPrice() * quantity);
                //decrement product inStock variable
                cartItem.getProduct().setInStock(availableInStock - quantity);
            } else {
                //Todo("Handle unavailable product exception");
                System.out.println("Product hasn't enough instances in stock");
                break;
            }
        }
        order.setTotal(total);
        order.setOrderItems(orderItems);
        return order;
    }

    @Override
    public boolean addItem(Long cartId, CartItem item) {
        try (Session session = config.getSessionFactory().openSession()) {
            Cart cart = get(cartId);
            List<CartItem> items = cart.getItems();
            //checks if item is already in cart then increments quantity
            List<CartItem> matchingProduct = items.stream()
                    .filter(i->i.getProduct().getId().equals(item.getProduct().getId()))
                    .collect(Collectors.toList());
            if(!matchingProduct.isEmpty()){
                int newQuantity = incrementProductQuantity(cartId,item.getProduct().getId(),item.getQuantity());
                if(newQuantity>0)
                    return true;
                return false;
            }
            else {
                Transaction transaction = session.beginTransaction();
                session.persist(item);
                items.add(item); //add item to cart list
                session.update(cart); //update cart
                transaction.commit();
                session.close();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<CartItem> getCartItems(Long cartId) {
        return get(cartId).getItems();
    }

    @Override
    public int setProductQuantity(Long cartId, Long itemId, int newQuantity) {
        try (Session session = config.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Cart cart = get(cartId);
            for (CartItem item : cart.getItems()) {
                if (item.getId().equals(itemId))
                    item.setQuantity(newQuantity);
            }
            session.update(cart); //update cart
            transaction.commit();
            session.close();
            return newQuantity;
        } catch (HibernateException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int incrementProductQuantity(Long cartId, Long productId,int quantity) {
        try (Session session = config.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Cart cart = get(cartId);
            int newQuantity = 0;
            CartItem foundItem=null;
            for (CartItem item : cart.getItems()) {
                if (item.getProduct().getId().equals(productId)) {
                    newQuantity = item.getQuantity() + quantity;
                    item.setQuantity(newQuantity);
                    foundItem = item;
                }
            }
            session.update(cart); //update cart
            session.update(foundItem); //updates CartItem
            transaction.commit();
            session.close();
            return newQuantity;
        } catch (HibernateException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int decrementProductQuantity(Long cartId, Long productId) {
        try (Session session = config.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Cart cart = get(cartId);
            CartItem foundItem=null;
            int newQuantity = 0;
            for (CartItem item : cart.getItems()) {
                if (item.getProduct().getId().equals(productId)) {
                    newQuantity = item.getQuantity() - 1;
                    if (newQuantity >= 0) //to prevent negative quantities
                        item.setQuantity(newQuantity);
                    foundItem = item;
                }
            }
            session.update(cart); //update cart
            session.update(foundItem); //updates cartItem
            transaction.commit();
            session.close();
            return newQuantity;
        } catch (HibernateException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
