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
    public List<Order> findAll(int customerId) {
        List<Order> list;
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            list = session.createQuery("from orders where o.customerId= " + customerId, Order.class)
                    .list();
        }
        catch (HibernateException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
        return list;
    }

    @Override
    public Order findOne(int orderId) {
        Order order = null;
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
    public boolean save(Order order) {
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
    public boolean updateOne(int orderId, Order order) {
        return false;
    }

    @Override
    public boolean deleteOne(int orderId) {
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
}
