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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@NoArgsConstructor
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
            Cart cart = get(cartId);
            cart.getItems().removeIf(item -> Objects.equals(item.getId(), itemId)); //remove desired item
            session.update(cart); //update cart
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
        List<OrderItem> orderItems = new ArrayList<>();
        Cart cart = get(cartId);
        //iterate over each cart item to transform it to order item.
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            //add product from cartItem to orderItem
            //add quantity
            //Todo: complete transformation validation -> waiting for Miand to add setters for OrderItem
        }
        Order order = new Order();
        order.setDate(Date.valueOf(LocalDate.now()));
        order.setCustomer(cart.getCustomer());
        //todo: set orderItems list to Order
        return order;
    }

    @Override
    public boolean addItem(Long cartId, CartItem item) {
        try (Session session = config.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Cart cart = get(cartId);
            cart.getItems().add(item); //add item to cart list
            session.update(cart); //update cart
            transaction.commit();
            session.close();
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
}
