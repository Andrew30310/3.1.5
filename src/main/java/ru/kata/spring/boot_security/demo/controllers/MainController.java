package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.validators.UserValidator;

import java.util.List;

@Controller
public class MainController {

    private final UserValidator userValidator;
    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public MainController(UserValidator userValidator, UserService userService, RoleRepository roleRepository) {
        this.userValidator = userValidator;
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/admin")
    public String showUsers(ModelMap model) {
        model.addAttribute("users", userService.getUsersList());
        return "allUsers";
    }

    @GetMapping("/admin/{id}")
    public String showUser(@PathVariable("id") long id, ModelMap model) {
        model.addAttribute("user", userService.getUser(id));
        return "oneUserForAdmin";
    }


    @GetMapping("/admin/new")
    public String createModel(@ModelAttribute("user") User user, ModelMap model) {
        model.addAttribute("user", user);
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "new";
    }

    @PostMapping("/admin/new/create")
    public String createUser(@ModelAttribute("user") User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "new";
        }

        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String updateModel(ModelMap model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUser(id));
        return "update";
    }

    @PatchMapping("/admin/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/user/{id}")
    public String showUserToUser(@PathVariable("id") long id, ModelMap model) {
        model.addAttribute("user", userService.getUser(id));
        return "oneUserForUser";
    }
}
