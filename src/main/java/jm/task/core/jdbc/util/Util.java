package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";
    // работает. configure только для использования с xml? getCurrentSession не работает без него, меняю на openSession
    private static SessionFactory sessionFactory;
    private static final SessionFactory SESSION_FACTORY = new Configuration().configure("hibernate.cfg.xml")
            .addAnnotatedClass(User.class).buildSessionFactory();


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, LOGIN, PASSWORD);
    }

//    public static SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }


    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hibernate.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        configuration.setProperties(properties);
        configuration.addAnnotatedClass(User.class);
        return sessionFactory = configuration.buildSessionFactory();
    }

// слишком отстой
    //    public static SessionFactory getSessionFactory() {
//        Configuration configuration = new Configuration();
//        Properties properties = new Properties();
//        properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
//        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
//        properties.put(Environment.URL, "jdbc:mysql://localhost:3306/test");
//        properties.put(Environment.USER, "root");
//        properties.put(Environment.PASS, "root");
//        properties.put(Environment.SHOW_SQL, "true");
////        properties.put(Environment.HBM2DDL_AUTO, "true");
//        configuration.setProperties(properties);
//        configuration.addAnnotatedClass(User.class);
//        sessionFactory = configuration.buildSessionFactory();
//        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//                .applySettings(configuration.getProperties()).build();
//        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
//        return sessionFactory;
//    }
    public static Statement getStatement() throws SQLException {
        return Util.getConnection().createStatement();
    }

}
