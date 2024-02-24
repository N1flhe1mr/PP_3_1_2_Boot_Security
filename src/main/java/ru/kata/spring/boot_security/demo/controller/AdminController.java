package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/users/list")
    public String getUsers(ModelMap model) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        return "list";
    }

    @GetMapping("/admin/users/edit")
    public String editUser(@RequestParam("id") long id, ModelMap model) {
        User user = userService.findUserById(id);
        user.setRoles(userService.getRoles());
        model.addAttribute("user", user);
        return "edit";
    }

    @GetMapping("/admin/users/add")
    public String newUser(ModelMap model) {
        User user = new User();
        user.setRoles(userService.getRoles());
        model.addAttribute("user", user);
        return "add";
    }

    @PostMapping("/admin/users/create")
    public String createUser(@ModelAttribute("user") User user) {
        if (user.getName().isEmpty() ||
                user.getSurname().isEmpty() ||
                user.getUsername().isEmpty() ||
                user.getEmail().isEmpty() ||
                user.getPassword().isEmpty() ||
                user.getAge() == 0  ||
                user.getRoles().isEmpty()) {
            return "redirect:admin/users/add";
        }
        userService.save(user);
        return "redirect:/admin/users/list";
    }

    @PatchMapping("/admin/users/update")
    public String updateUser(@ModelAttribute("user") User user) {
        if (user.getName().isEmpty() ||
                user.getSurname().isEmpty() ||
                user.getUsername().isEmpty() ||
                user.getEmail().isEmpty() ||
                user.getPassword().isEmpty() ||
                user.getAge() == 0 ||
                user.getRoles().isEmpty()) {
            return "redirect:/admin/users/add";
        }

        userService.save(user);
        return "redirect:/admin/users/list";
    }

    @GetMapping("/admin/users/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users/list";
    }
}