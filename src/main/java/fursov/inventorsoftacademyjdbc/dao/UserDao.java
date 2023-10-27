package fursov.inventorsoftacademyjdbc.dao;

import fursov.inventorsoftacademyjdbc.domain.User;

import java.util.Optional;
import java.util.List;

public interface UserDao {
    boolean addUser(User user);
    Optional<User> getUserById(long id);
    Optional<User> getUserByEmail(String email);
    boolean updateUser(long id, User user);
    boolean deleteUser(long id);
    List<User> getAllUsers();
}