package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();

    User findUserById(long id);

    void deleteById(long id);

    User save(User user);

    User findUserByUsername(String username);
}
