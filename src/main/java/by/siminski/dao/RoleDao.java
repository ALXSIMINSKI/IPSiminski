package by.siminski.dao;

import by.siminski.model.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface RoleDao extends JpaRepository<Role, BigInteger> {
}
