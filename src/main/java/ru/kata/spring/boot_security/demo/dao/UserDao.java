package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {

    boolean updateUser(long oldUsersId, User newUser);

    User getUser(long id);

    List<User> getUsersList();

    void addUser(User user);

    void deleteUser(long id);
}