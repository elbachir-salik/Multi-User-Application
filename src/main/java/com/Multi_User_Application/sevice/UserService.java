package com.Multi_User_Application.sevice;

import com.Multi_User_Application.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User createUser(User user);
    User updateUser(Long id, User updateUser);
    void deleteUser(Long id);
    void saveUser(User user);
    Optional<User> findByUsername(String username);
}
