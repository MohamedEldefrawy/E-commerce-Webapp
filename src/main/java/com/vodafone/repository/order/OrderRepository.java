package com.vodafone.repository.order;

import com.vodafone.config.HibernateConfig;
import com.vodafone.model.Order;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository implements IOrderRepository {

    private final HibernateConfig hibernateConfig;


    public OrderRepository(HibernateConfig hibernateConfig) {
        this.hibernateConfig = hibernateConfig;
    }

    @Override
    public List<Order> getAll() {
        List<Order> list;
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            //list = session.createQuery("From orders where o.customerId= " + customerId, Order.class)
            list = session.createQuery("From orders", Order.class)
                    .list();
        }
        catch (HibernateException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
        return list;
    }

    @Override
    public Order get(Long orderId) {
        Order order ;
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery(
                    "From orders o where o.id=" +orderId
            );
            order = (Order) query.uniqueResult();
            tx.commit();
        }
        catch (HibernateException e){
            e.printStackTrace();
            return null;
        }
        return order;
    }

    @Override
    public boolean create(Order order) {
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(order);
            tx.commit();
            return true;
        }
        catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean update(Long orderId, Order updatedEntity) {
        Order order = get(orderId);
        if (order == null)
            return false;

        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
            //doesnt update order items set
            Transaction tx  = session.beginTransaction();
            order.setCustomer(updatedEntity.getCustomer());
            order.setDate(updatedEntity.getDate());
            session.persist(order);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Long orderId) {
        int modifications = 0;
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery(
                    "delete orders o where o.id= " + orderId
            );
            modifications = query.executeUpdate();
            tx.commit();
        }
        catch (HibernateException e){
            e.printStackTrace();
            return false;
        }
        return modifications > 0;
    }

    @Override
    public List<Order> getByCustomerId(Long customerId) {
        List<Order> list;
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            list = session.createQuery("From orders where o.customerId= " + customerId, Order.class)
                    .list();
        }
        catch (HibernateException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
        return list;
    }
}
