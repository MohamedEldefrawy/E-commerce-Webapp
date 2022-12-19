//package com.vodafone.Ecommerce.repository.admin;
//
//import com.vodafone.Ecommerce.config.HibernateConfig;
//import com.vodafone.Ecommerce.exception.admin.CreateAdminException;
//import com.vodafone.Ecommerce.model.Admin;
//import org.hibernate.HibernateException;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.NoResultException;
//import java.util.List;
//import java.util.Optional;
//
//
//@Repository
//public class AdminRepository implements IAdminRepository {
//
//    private final HibernateConfig hibernateConfig;
//    private final Logger logger = LoggerFactory.getLogger(AdminRepository.class);
//
//    public AdminRepository(HibernateConfig hibernateConfig) {
//        this.hibernateConfig = hibernateConfig;
//    }
//
//    @Override
//    public Optional<List<Admin>> getAll() {
//        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
//            List<Admin> list = session.createQuery("from Admin", Admin.class)
//                    .list();
//            return Optional.ofNullable(list);
//        }
//    }
//
//    @Override
//    public Optional<Admin> getById(Long id) {
//        Admin admin;
//        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
//            admin = session.get(Admin.class, id);
//        } catch (HibernateException e) {
//            logger.warn(e.getMessage());
//            return Optional.empty();
//        }
//        return Optional.ofNullable(admin);
//    }
//
//    @Override
//    public boolean delete(Long id) {
//        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
//            Transaction tx = session.beginTransaction();
//            Admin admin = getById(id).get();
//            if (admin != null) {
//                session.delete(admin);
//                tx.commit();
//                return true;
//            }
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return false;
//    }
//
//    @Override
//    public Optional<Long> create(Admin admin) {
//        if (admin.getPassword() == null)
//            admin.setPassword(getAlphaNumericString(8));
//        if (getByUsername(admin.getUserName()) == null && getByEmail(admin.getEmail()) == null) {
//            try (Session session = hibernateConfig.getSessionFactory().openSession()) {
//                Transaction tx = session.beginTransaction();
//                Long id = (Long) session.save(admin);
//                tx.commit();
//                return Optional.ofNullable(id);
//            } catch (HibernateException e) {
//                logger.warn(e.getMessage());
//                return Optional.empty();
//            }
//        } else throw new CreateAdminException("Username or email already exists");
//    }
//
//    @Override
//    public boolean update(Long id, Admin updatedEntity) {
//        Transaction tx;
//        Admin admin = getById(id).get();
//        if (admin == null)
//            return false;
//
//        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
//            //doesnt update password or role
//            tx = session.beginTransaction();
//            admin.setEmail(updatedEntity.getEmail());
//            admin.setUserName(updatedEntity.getUserName());
//            session.update(admin);
//            tx.commit();
//            return true;
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean updatePassword(Long id, String newPassword) {
//        Admin admin = getById(id).get();
//        if (admin == null)
//            return false;
//
//        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
//            //doesnt update password or role
//            Transaction tx = session.beginTransaction();
//            admin.setPassword(newPassword);
//            session.update(admin);
//            tx.commit();
//            return true;
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    private String getAlphaNumericString(int n) {
//        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz_&%";
//
//        StringBuilder sb = new StringBuilder(n);
//
//        for (int i = 0; i < n; i++) {
//            int index
//                    = (int) (AlphaNumericString.length()
//                    * Math.random());
//            sb.append(AlphaNumericString
//                    .charAt(index));
//        }
//        return sb.toString();
//    }
//
//    @Override
//    public boolean setFirstLoginFlag(Long id) {
//        Admin admin = getById(id).get();
//        if (admin == null)
//            return false;
//        try (Session session = this.hibernateConfig.getSessionFactory().openSession()) {
//            Transaction tx = session.beginTransaction();
//            admin.setFirstLogin(false);
//            session.update(admin);
//            tx.commit();
//            return true;
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public Optional<Admin> getByUsername(String username) {
//        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
//            try {
//                Admin admin =  session.createQuery("SELECT a from Admin a where a.userName=: username", Admin.class)
//                        .setParameter("username", username).getSingleResult();
//                return Optional.ofNullable(admin);
//            } catch (NoResultException e) {
//                return Optional.empty();
//            }
//        }
//        /*catch (HibernateException hibernateException) {
//            hibernateException.printStackTrace();
//            return Optional.empty();
//        }*/
//    }
//
//    public Optional<Admin> getByEmail(String email) {
//        try (Session session = hibernateConfig.getSessionFactory().openSession()) {
//            try {
//                Admin admin= session.createQuery("SELECT a from Admin a where a.email=: email", Admin.class)
//                        .setParameter("email", email).getSingleResult();
//                return Optional.of(admin);
//            } catch (NoResultException e) {
//                return Optional.empty();
//            }
//        }
//        /*catch (HibernateException hibernateException) {
//            hibernateException.printStackTrace();
//            return null;
//        }*/
//    }
//}
//
