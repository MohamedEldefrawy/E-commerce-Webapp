package com.vodafone.config;

import com.vodafone.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Properties;

@Configuration
@PropertySource("classpath:database.properties")
public class HibernateConfig {
    private SessionFactory sessionFactory;
    @Value("${CONNECTION_STRING}")
    public String CONNECTION_STRING;
    @Value("${USER_NAME}")
    public String USER_NAME;
    @Value("${PASSWORD}")
    public String PASSWORD;
    @Value("${JDBC_DRIVER}")
    public String JDBC_DRIVER;

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, this.JDBC_DRIVER);
                settings.put(Environment.URL, this.CONNECTION_STRING);
                settings.put(Environment.USER, this.USER_NAME);
                settings.put(Environment.PASS, this.PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.HBM2DDL_AUTO, "create-drop");
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
