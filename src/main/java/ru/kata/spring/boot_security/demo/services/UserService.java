package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService{
    public User findByUsername(String username);

    void updateUser(User user);

    User getUser(long id);

    List<User> getUsersList();

    void addUser(User user);

    void deleteUser(long id);
}
