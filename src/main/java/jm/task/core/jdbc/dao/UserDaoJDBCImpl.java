package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection;

    public UserDaoJDBCImpl() {
        this.connection = Util.getConnection();
    }
    @Override
    public void createUsersTable() {
        try(Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users" +
                    "(id INT NOT NULL AUTO_INCREMENT,  firstName VARCHAR(40), lastName VARCHAR(40), age INT, PRIMARY KEY (id))");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    @Override
    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            System.out.println("table dropped");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
           try (Connection connection = Util.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(firstName, lastName, age) VALUES(?, ?, ?)")) {
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
         try (Connection connection = Util.getConnection();
              PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
             preparedStatement.setLong(1, id);
             preparedStatement.executeUpdate();
         } catch (SQLException e) {
             e.printStackTrace();

         }
    }
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            String userId = "1";
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                user.setId(id);
                users.add(user);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    @Override
    public void cleanUsersTable() {
        String cleanTable = "DROP TABLE IF EXISTS users";
       try (Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()) {
           statement.execute(cleanTable);
           System.out.println("clean table success");
       } catch (SQLException e) {
           e.printStackTrace();
       }
    }
}
