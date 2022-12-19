//package com.vodafone.Ecommerce.repository.orderItems;
//
//import com.vodafone.Ecommerce.config.HibernateConfig;
//import com.vodafone.Ecommerce.model.OrderItem;
//import org.hibernate.HibernateException;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import org.hibernate.query.Query;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public class OrderItemsRepository implements IOrderItemsRepository {
//
//    private final HibernateConfig hibernateConfig;
//    private final Logger logger = LoggerFactory.getLogger(OrderItemsRepository.class);
//
//    public OrderItemsRepository(HibernateConfig hibernateConfig) {
//        this.hibernateConfig = hibernateConfig;
//    }
//
//    @Override
//    public Optional<Long> create(OrderItem orderItem) {
//        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
//            Transaction tx = session.beginTransaction();
//            Long id = (Long) session.save(orderItem);
//            tx.commit();
//            return Optional.ofNullable(id);
//        } catch (HibernateException e) {
//            logger.warn(e.getMessage());
//            return Optional.empty();
//        }
//    }
//
//
//    @Override
//    public Optional<List<OrderItem>> getAll() {
//        List<OrderItem> list;
//        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
//            list = session.createQuery("from OrderItem", OrderItem.class).list();
//        } catch (HibernateException e) {
//            return Optional.of(new ArrayList<>());
//        }
//        return Optional.ofNullable(list);
//    }
//
//    @Override
//    public Optional<OrderItem> getById(Long id) {
//        OrderItem orderItem;
//        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
//            orderItem = session.get(OrderItem.class, id);
//            session.close();
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            return Optional.empty();
//        }
//        return Optional.ofNullable(orderItem);
//    }
//
//    @Override
//    public boolean delete(Long id) {
//        int modifications;
//        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
//            Transaction tx = session.beginTransaction();
//            Query query = session.createQuery("delete OrderItem  oi where oi.id=" + id);
//            modifications = query.executeUpdate();
//            tx.commit();
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return modifications > 0;
//    }
//
//    @Override
//    public boolean update(Long id, OrderItem updatedEntity) {
//        OrderItem orderItem = getById(id).get();
//        if (orderItem == null) return false;
//
//        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
//            //doesnt update product or order
//            Transaction tx = session.beginTransaction();
//            orderItem.setQuantity(updatedEntity.getQuantity());
//            session.persist(orderItem);
//            tx.commit();
//            return true;
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//}
