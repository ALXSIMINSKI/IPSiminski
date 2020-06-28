package by.siminski.services.user;

import by.siminski.model.security.User;
import java.util.List;

public interface UserService {

    void save(User user);

    User findByUsername(String username);

    List<User> findAllUsers();
}

