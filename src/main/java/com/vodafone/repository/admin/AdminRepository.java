package com.vodafone.repository.admin;

import com.vodafone.config.HibernateConfig;
import com.vodafone.model.Admin;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class AdminRepository implements IAdminRepository {

    private final HibernateConfig hibernateConfig;

    public AdminRepository(HibernateConfig hibernateConfig) {
        this.hibernateConfig = hibernateConfig;
    }

    @Override
    public List<Admin> getAll() {
        List<Admin> list;
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            list = session.createQuery("from Admin", Admin.class)
                    .list();
        } catch (HibernateException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return list;
    }

    @Override
    public Admin get(Long id) {
        Admin admin = null;
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            admin = session.get(Admin.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
        return admin;
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Admin admin = get(id);
            if (admin != null) {
                session.delete(admin);
                tx.commit();
                return true;
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public boolean create(Admin admin) {
        if (admin.getPassword() == null)
            admin.setPassword(getAlphaNumericString(8));
        if(getByUsername(admin.getUserName())==null && getByEmail(admin.getEmail())==null) {
            try (Session session = hibernateConfig.getSessionFactory().openSession()) {
                Transaction tx = session.beginTransaction();
                session.persist(admin);
                tx.commit();
                return true;
            } catch (HibernateException e) {
                e.printStackTrace();
                return false;
            }
        }else return false;
    }

    @Override
    public boolean update(Long id, Admin updatedEntity) {
        if(getByUsername(updatedEntity.getUserName())==null && getByEmail(updatedEntity.getEmail())==null) {
            Transaction tx;
            Admin admin = get(id);
            if (admin == null)
                return false;

            try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
                //doesnt update password or role
                tx = session.beginTransaction();
                admin.setEmail(updatedEntity.getEmail());
                admin.setUserName(updatedEntity.getUserName());
                session.update(admin);
                tx.commit();
                return true;
            } catch (HibernateException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
    public boolean updatePassword(Long id, String newPassword){
        Admin admin = get(id);
        if (admin == null)
            return false;

        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
            //doesnt update password or role
            Transaction tx = session.beginTransaction();
            admin.setPassword(newPassword);
            session.update(admin);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getAlphaNumericString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz"
                + "_&%";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }

    @Override
    public void setFirstLoginFlag(Long id) {
        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
            //doesnt update password or role
            Transaction tx = session.beginTransaction();
            Admin admin = session.get(Admin.class, id);
            admin.setFirstLogin(false);
            session.update(admin);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
    public Admin getByUsername(String userName) {
        List<Admin> admins = getAll().stream()
                .filter(a -> a.getUserName().equals(userName))
                .collect(Collectors.toList());
        if(admins.isEmpty()) return null;
        return admins.get(0);
    }
    public Admin getByEmail(String email) {
        List<Admin> admins = getAll().stream()
                .filter(a -> a.getEmail().equals(email))
                .collect(Collectors.toList());
        if(admins.isEmpty()) return null;
        return admins.get(0);
    }
}

