package com.vodafone.repository.admin;

import com.vodafone.config.HibernateConfig;
import com.vodafone.model.Admin;
import com.vodafone.model.Order;
import com.vodafone.model.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;


@Repository
public class AdminRepository implements IAdminRepository{

    private final HibernateConfig hibernateConfig;

    public AdminRepository(HibernateConfig hibernateConfig) {
        this.hibernateConfig = hibernateConfig;
    }

    @Override
    public List<Admin> getAll() {
        List<Admin> list;
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            list = session.createQuery("from admins", Admin.class)
                    .list();
        }
        catch (HibernateException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
        return list;
    }

    @Override
    public Admin get(Long id) {
        Admin admin = null;
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            admin = session.get(Admin.class,id);
        }
        catch (HibernateException e){
            e.printStackTrace();
            return null;
        }
        return admin;
    }

    @Override
    public boolean delete(Long id) {
        int modifications = 0;
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery(
                    "delete admins a where a.id=" + id
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
    public boolean create(Admin admin) {
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(admin);
            tx.commit();
            return true;
        }
        catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Long id, Admin updatedEntity) {
        Admin admin = get(id);
        if (admin == null)
            return false;

        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
            //doesnt update password or role
            Transaction tx  = session.beginTransaction();
            admin.setEmail(updatedEntity.getEmail());
            admin.setUserName(updatedEntity.getUserName());
            session.persist(admin);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

}
