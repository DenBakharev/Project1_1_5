package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String NEW_USER = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
    private static final String CREATE_TABLE = "create table if not exists users (id bigint PRIMARY KEY AUTO_INCREMENT" +
            ", name varchar(45), lastName varchar(45), age tinyint);";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS users";
    private static final String DELETE_FROM_ID = "DELETE FROM users WHERE ID = id";
    private static final String GET_ALL_USERS = "SELECT * FROM users";
    private static final String CLEAN_TABLE = "DELETE FROM users";

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        try (
                Connection connection = Util.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Util.closeConnection();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Connection connection = Util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Util.closeConnection();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (
                Connection connection = Util.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(NEW_USER)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

            System.out.println("User с именем – " + name + " добавлен в базу данных");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Util.closeConnection();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            Connection connection = Util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FROM_ID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Util.closeConnection();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            Connection connection = Util.getConnection();
            List<User> userList = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Util.closeConnection();
        }

    }

    @Override
    public void cleanUsersTable() {
        try {
            Connection connection = Util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CLEAN_TABLE);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Util.closeConnection();
        }
    }
}
