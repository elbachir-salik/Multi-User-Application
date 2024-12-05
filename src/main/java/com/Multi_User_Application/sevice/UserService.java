package com.Multi_User_Application.sevice;

import com.Multi_User_Application.entities.User;

import java.util.List;

public interface UserService {
    List<User> getAllusers();
    User getUserById(Long id);
    User createUser(User user);
    User updateUser(Long id, User updateUser);
    void deleteUser(Long id);
}
