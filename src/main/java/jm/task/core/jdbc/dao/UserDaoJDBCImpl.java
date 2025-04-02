package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }
    @Override
    public void createUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS tabletwo" +
                    "(id INT NOT NULL AUTO_INCREMENT,  firstName VARCHAR(40), lastName VARCHAR(40), age INT, PRIMARY KEY (id))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
           try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tabletwo(firstName, lastName, age) VALUES(?, ?, ?)")) {
               preparedStatement.setString(1, name);
               preparedStatement.setString(2, lastName);
               preparedStatement.setByte(3, age);
               preparedStatement.executeUpdate();
           } catch (SQLException e) {
               e.printStackTrace();
           }
    }
    @Override
    public void removeUserById(long id) {
         try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
             preparedStatement.setLong(1, id);
             preparedStatement.executeUpdate();
         } catch (SQLException e) {
             e.printStackTrace();

         }
    }
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
           ResultSet resultSet = statement.executeQuery("SELECT * FROM tabletwo");
           while (resultSet.next()) {
               User user = new User();
               user.setId(resultSet.getLong("id"));
               user.setName(resultSet.getString("firstName"));
               user.setLastName(resultSet.getString("lastName"));
               user.setAge(resultSet.getByte("age"));
               users.add(user);
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    @Override
    public void cleanUsersTable() {
       try (Statement statement = connection.createStatement()) {
           statement.executeUpdate("DROP TABLE IF EXISTS users");
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
    }
}
