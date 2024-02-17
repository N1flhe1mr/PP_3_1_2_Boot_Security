package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Component
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public DataLoader(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(String... args) {

        Role adminRole = roleRepository.findRoleByName("ADMIN").orElseGet(() -> {
            Role role = new Role();
            role.setId(1L);
            role.setName("ADMIN");
            return roleRepository.save(role);
        });

        Role userRole = roleRepository.findRoleByName("USER").orElseGet(() -> {
            Role role = new Role();
            role.setId(2L);
            role.setName("USER");
            return roleRepository.save(role);
        });

        if (userRepository.findUserByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setId(1L);
            admin.setUsername("admin");
            admin.setPassword(bCryptPasswordEncoder.encode("admin"));
            admin.setRoles(new HashSet<>(Arrays.asList(userRole, adminRole)));
            admin.setName("Admin");
            admin.setSurname("Admin");
            admin.setAge((byte) 30);
            admin.setEmail("6bqzK@example.com");
            userRepository.save(admin);
        }

        if (userRepository.findUserByUsername("user").isEmpty()) {
            User user = new User();
            user.setId(2L);
            user.setUsername("user");
            user.setPassword(bCryptPasswordEncoder.encode("user"));
            user.setRoles(Collections.singleton(userRole));
            user.setName("User");
            user.setSurname("User");
            user.setAge((byte) 20);
            user.setEmail("u7wPZ@example.com");
            userRepository.save(user);
        }
    }
}
