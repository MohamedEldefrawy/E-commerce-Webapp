package com.vodafone.repository.orderItems;

import com.vodafone.config.HibernateConfig;
import com.vodafone.model.OrderItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class OrderItemsRepository implements IOrderItemsRepository{

    private final HibernateConfig hibernateConfig;

    public OrderItemsRepository(HibernateConfig hibernateConfig) {
        this.hibernateConfig = hibernateConfig;
    }

    @Override
    public boolean create(OrderItem orderItem) {
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(orderItem);
            tx.commit();
            return true;
        }
        catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public List<OrderItem> getAll() {
        List<OrderItem> list;
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            list = session.createQuery("from OrderItem", OrderItem.class)
                    .list();
        }
        catch (HibernateException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
        return list;
    }

    @Override
    public OrderItem getById(Long id) {
        OrderItem orderItem = null;
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            orderItem = session.get(OrderItem.class,id);
            session.close();
        }
        catch (HibernateException e){
            e.printStackTrace();
            return null;
        }
        return orderItem;
    }

    @Override
    public boolean delete(Long id) {
        int modifications = 0;
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery(
                    "delete orderItems oi where oi.id=" + id
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
    public boolean update(Long id, OrderItem updatedEntity) {
        OrderItem orderItem = getById(id);
        if (orderItem == null)
            return false;

        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
            //doesnt update product or order
            Transaction tx  = session.beginTransaction();
            orderItem.setQuantity(updatedEntity.getQuantity());
            session.persist(orderItem);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }
}
