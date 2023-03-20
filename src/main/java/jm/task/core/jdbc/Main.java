package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Bob", "Bobinsky", (byte) 20);
        userService.saveUser("Karl", "Karlov", (byte) 25);
        userService.saveUser("Rick", "Petrov", (byte) 35);
        userService.saveUser("Morty", "Sidorov", (byte) 40);

        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
