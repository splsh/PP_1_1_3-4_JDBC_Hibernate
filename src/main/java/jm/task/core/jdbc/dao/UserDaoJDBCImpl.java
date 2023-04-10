package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Statement statement = Util.getStatement();
             ResultSet resultSet = statement.executeQuery("SHOW TABLES LIKE 'Users'")) {
            while (resultSet.next()) {
                if (resultSet.getString(1).equals("users")) {
                    System.out.println("Таблица уже существует");
                    return;
                }
            }
            statement.execute("CREATE TABLE Users ( id int NOT NULL AUTO_INCREMENT" +
                    ", name VARCHAR(50) NOT NULL" +
                    ", surname VARCHAR(50) NOT NULL" +
                    ", age INT NOT NULL, PRIMARY KEY (id));");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.getStatement();
             ResultSet resultSet = statement.executeQuery("SHOW TABLES LIKE 'Users'")) {
            if (!resultSet.next() || !resultSet.getString(1).equals("users")) {
                System.out.println("Таблицы не существует");
                return;
            }
            statement.execute("DROP TABLE Users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Users (name, surname, age) values (?, ?, ?)")
            ) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setInt(3, age);
                preparedStatement.execute();
                connection.commit();
                System.out.println("User с именем " + name + " был добавлен в таблицу");
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Сохранение пользователя не удалось");
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()){
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = Util.getConnection()
                    .prepareStatement("DELETE from Users where id = ?;")) {
                preparedStatement.setLong(1, id);
                preparedStatement.execute();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Удаление не удалось");
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = Util.getStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Users ")) {
            while (resultSet.next()) {
                User user = new User(resultSet.getString(2), resultSet.getString(3)
                        , resultSet.getByte(4));
                user.setId(resultSet.getLong(1));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.getStatement()) {
            statement.execute("TRUNCATE TABLE Users;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
