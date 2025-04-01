package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Артем", "Арсеньев", (byte) 36);
        System.out.println("User с именем — Артем добавлен в базу данных");
        userService.saveUser("Дмитрий", "Фролов", (byte) 38);
        System.out.println("User с именем — Дмитрий добавлен в базу данных");
        userService.saveUser("Иван", "Иванов", (byte) 20);
        System.out.println("User с именем — Иван добавлен в базу данных");
        userService.saveUser("Петр", "Петров", (byte) 25);
        System.out.println("User с именем — Петр добавлен в базу данных");

        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();

    }
}
