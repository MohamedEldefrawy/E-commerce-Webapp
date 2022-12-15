package com.vodafone.repository.cart;

import com.vodafone.config.HibernateConfig;
import com.vodafone.model.Cart;
import com.vodafone.model.CartItem;
import lombok.AllArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class CartRepository implements ICartRepository {
    private HibernateConfig config;
    private final Logger logger = LoggerFactory.getLogger(CartRepository.class);


    @Override
    public Optional<Long> create(Cart entity) {
        Long id;
        try (Session session = config.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            id = (Long) session.save(entity);
            transaction.commit();
        } catch (HibernateException e) {
            logger.warn(e.getMessage());
            return Optional.empty();
        }
        return Optional.ofNullable(id);
    }

    @Override
    public boolean update(Long id, Cart updatedEntity) {
        try (Session session = config.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            updatedEntity.setId(id);
            session.update(updatedEntity);
            transaction.commit();
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
            Optional<Cart> cart = getById(id);
            if (!cart.isPresent())
                return false;
            session.delete(cart.get());
            transaction.commit();

        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Optional<Cart> getById(Long id) {
        Cart cart = null;
        try (Session session = config.getSessionFactory().openSession()) {
            cart = session.get(Cart.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.ofNullable(cart);
    }

    @Override
    public Optional<List<Cart>> getAll() {
        List<Cart> carts;
        try (Session session = config.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            carts = session.createQuery("SELECT DISTINCT cart From Cart cart", Cart.class).getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.ofNullable(carts);
    }

    @Override
    public boolean removeItem(Cart cart, Long itemId) {
        try (Session session = config.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            //remove cartItem from cart arrayList
            cart.getItems().removeIf(item -> Objects.equals(item.getId(), itemId)); //remove desired item
            session.update(cart); //update cart
            //Remove cartItem from DB
            CartItem cartItem = session.get(CartItem.class, itemId);
            session.delete(cartItem);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean clearCart(Cart cart) {
        try (Session session = config.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            //delete all cart items from database
            cart.getItems().forEach(session::delete);
            //delete cart object
            cart.getItems().clear();
            session.update(cart);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
        try (Session session = config.getSessionFactory().openSession()) {
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
                    Transaction transaction = session.beginTransaction();
                    session.persist(item);
                    items.add(item); //add item to cart list
                    session.update(cart); //update cart
                    transaction.commit();

                } else {
                    return 0;
                }
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            return -1;
        }
        return item.getQuantity();
    }

    @Override
    public int setProductQuantity(Cart cart, Long itemId, int newQuantity) {
        try (Session session = config.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            for (CartItem item : cart.getItems()) {
                if (item.getId().equals(itemId))
                    item.setQuantity(newQuantity);
            }
            session.update(cart); //update cart
            transaction.commit();
            return newQuantity;
        } catch (HibernateException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int incrementProductQuantity(Cart cart, Long productId, int quantity) {
        try (Session session = config.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            int newQuantity = 0;
            CartItem foundItem = null;
            for (CartItem item : cart.getItems()) {
                if (item.getProduct().getId().equals(productId)) {
                    newQuantity = item.getQuantity() + quantity;
                    if (item.getProduct().getInStock() >= newQuantity) {
                        item.setQuantity(newQuantity);
                        foundItem = item;
                    } else {
                        return 0;
                    }
                }
            }
            session.update(cart); //update cart
            session.update(foundItem); //updates CartItem
            transaction.commit();
            return newQuantity;
        } catch (HibernateException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int decrementProductQuantity(Cart cart, Long productId, int quantity) {
        try (Session session = config.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            CartItem foundItem = null;
            int newQuantity = 0;
//            cart.getItems().stream().filter(item -> item.getId().equals(productId)).forEach(item -> {
//                item.setQuantity(item.getQuantity() - 1);
//            });
            for (CartItem item : cart.getItems()) {
                if (item.getProduct().getId().equals(productId)) {
                    newQuantity = item.getQuantity() - quantity;
                    if (newQuantity >= 0) //to prevent negative quantities
                        item.setQuantity(newQuantity);
                    foundItem = item;
                }
            }
            session.update(cart); //update cart
            session.update(foundItem); //updates cartItem
            transaction.commit();
            return newQuantity;
        } catch (HibernateException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
