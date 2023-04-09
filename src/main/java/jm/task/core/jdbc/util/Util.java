package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.*;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";
    private static final SessionFactory SESSION_FACTORY = new Configuration().configure("hibernate.cfg.xml")
            .addAnnotatedClass(User.class).buildSessionFactory();
//    private static Connection connection;
//    private static Statement statement;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, LOGIN, PASSWORD);
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static Statement getStatement() throws SQLException {
        return Util.getConnection().createStatement();
    }

}
