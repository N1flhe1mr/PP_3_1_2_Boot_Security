package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    void save(User user);

    User findUserByUsername(String name);

    User findUserById(long id);

    void deleteUserById(long id);

    List<User> getUsers();

    List<Role> getRoles();

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
