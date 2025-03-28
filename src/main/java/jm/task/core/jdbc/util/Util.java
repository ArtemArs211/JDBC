package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {


    public class MySQLConnection {
        private static final String URL = "jdbc:mysql://localhost:3306/your_database";
        private static final String USER = "your_username";
        private static final String PASSWORD = "your_password";

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }

        public static void main(String[] args) {
            try (Connection connection = getConnection()) {
                System.out.println("Подключение к MySQL установлено!");
            } catch (SQLException e) {
                System.err.println("Ошибка подключения к MySQL:");
                e.printStackTrace();
            }
        }
    }
}
