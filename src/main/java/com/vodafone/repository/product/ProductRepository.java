package com.vodafone.repository.product;

import com.vodafone.config.HibernateConfig;
import com.vodafone.model.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository implements IProductRepository {
    private final HibernateConfig hibernateConfig;

    public ProductRepository(HibernateConfig hibernateConfig) {
        this.hibernateConfig = hibernateConfig;
    }

    @Override
    public boolean create(Product entity) {
        Transaction transaction;
        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Long id, Product updatedEntity) {
        Transaction transaction;
        Product selectedEntity = get(id);
        if (selectedEntity == null)
            return false;

        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            selectedEntity.setCategory(updatedEntity.getCategory());
            selectedEntity.setRate(updatedEntity.getRate());
            selectedEntity.setDescription(updatedEntity.getDescription());
            selectedEntity.setPrice(updatedEntity.getPrice());
            selectedEntity.setImage(updatedEntity.getImage());
            session.persist(selectedEntity);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        Transaction transaction;
        Product product = get(id);
        if (product == null)
            return false;
        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(product);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Product get(Long id) {
        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
            return session.get(Product.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Product> getAll() {
        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("from Product ", Product.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Product getByName(String name) {
        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
            return session.get(Product.class, name);
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Product> getByCategory(String category) {
        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("from Product p where p.category= :p", Product.class).setParameter("p", category).list();
        } catch (HibernateException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Product> getByPrice(double price) {
        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("from Product p where p.price= :p", Product.class).setParameter("p", price).list();
        } catch (HibernateException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Product> getByPriceRange(double low, double high) {
        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("from Product p where p.price in(:l,:h)", Product.class).setParameter("l", low).setParameter("h", high).list();
        } catch (HibernateException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Product> getByRate(float rate) {
        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("from Product p where p.rate= :r", Product.class).setParameter("r", rate).list();
        } catch (HibernateException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
