package by.siminski.dao;

import by.siminski.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface UserDao extends JpaRepository<User, BigInteger> {
    User findByUsername(String username);

    List<User> findAll();
}
