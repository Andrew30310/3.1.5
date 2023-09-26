package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        if (!userRepository.getOne(user.getId()).getPassword().equals(user.getPassword())) {
            user.setPassword(user.getPassword());
        }
        User userById = getUser(user.getId());
        if (userById != null) {
            if (user.getRoles().isEmpty() || user.getRoles() == null) {
                user.setRoles(userById.getRoles());
            }
            userRepository.saveAndFlush(user);
        }
    }


    @Override
    public User getUser(long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> getUsersList() {
        return userRepository.findAllAndFetchRolesEagerly();
    }

    @Override
    @Transactional
    public void addUser(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }


}
