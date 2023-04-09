package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;

import java.sql.*;

public class Main {


    public static void main(String[] args) throws SQLException {
        String name = "Coca";
        String lastName = "Cola";
        byte age = 3;
//        UserService userService = new UserServiceImpl();
//        userService.createUsersTable();
//        for (int i = 0; i < 4; i++) {
//            userService.saveUser(name, lastName, age);
//        }
//        System.out.println(userService.getAllUsers());
//        userService.cleanUsersTable();
//        userService.dropUsersTable();

        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();
        userDaoHibernate.saveUser(name, lastName, age);
    }
}
