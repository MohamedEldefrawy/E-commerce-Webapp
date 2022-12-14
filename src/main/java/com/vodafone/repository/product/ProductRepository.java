package com.vodafone.repository.product;

import com.vodafone.config.HibernateConfig;
import com.vodafone.model.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository implements IProductRepository {
    private final HibernateConfig hibernateConfig;

    public ProductRepository(HibernateConfig hibernateConfig) {
        this.hibernateConfig = hibernateConfig;
    }

    @Override
    public Optional<Long> create(Product entity) {
        Transaction transaction;
        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Long id = (Long) session.save(entity);
            transaction.commit();
            return Optional.ofNullable(id);
        }
    }

    @Override
    public boolean update(Long id, Product updatedEntity) {
        Transaction transaction;
        Product selectedEntity = getById(id).get();

        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            selectedEntity.setCategory(updatedEntity.getCategory());
            selectedEntity.setRate(updatedEntity.getRate());
            selectedEntity.setDescription(updatedEntity.getDescription());
            selectedEntity.setPrice(updatedEntity.getPrice());
            selectedEntity.setImage(updatedEntity.getImage());
            selectedEntity.setInStock(updatedEntity.getInStock());
            session.update(selectedEntity);
            transaction.commit();
            return true;
        }
    }

    @Override
    public boolean delete(Long id) {
        Transaction transaction;
        Product product = getById(id).get();
        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            product.setDeleted(true);
            session.update(product);
            transaction.commit();
            return true;
        }
    }

    @Override
    public Optional<Product> getById(Long id) {
        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Product.class, id));
        }
    }

    @Override
    public Optional<List<Product>> getAll() {
        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createQuery("from Product ", Product.class).list());
        }
    }

    @Override
    public Optional<List<Product>> getByName(String name) {
        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createQuery("FROM  Product p where p.name like :name and p.isDeleted=false", Product.class).setParameter("name", "%" + name + "%").list());
        }
    }

    @Override
    public Optional<List<Product>> getByCategory(String category) {
        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createQuery("from Product p where p.category like :p and p.isDeleted=false", Product.class).setParameter("p", "%" + category + "%").list());
        }
    }

    @Override
    public Optional<List<Product>> getAvailableProducts() {
        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createQuery("from Product p where p.isDeleted = :p", Product.class).setParameter("p", false).list());
        }
    }
}
