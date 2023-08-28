package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    public User findByUsername(String username);

    void updateUser(long oldUsersId, User newUser);

    User getUser(long id);

    List<User> getUsersList();

    void addUser(User user);

    void deleteUser(long id);
}
