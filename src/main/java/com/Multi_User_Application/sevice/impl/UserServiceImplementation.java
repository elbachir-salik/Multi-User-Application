package com.Multi_User_Application.sevice.impl;

import com.Multi_User_Application.entities.User;
import com.Multi_User_Application.exceptions.InvalidUserException;
import com.Multi_User_Application.exceptions.UserNotFoundException;
import com.Multi_User_Application.repo.UserRepository;
import com.Multi_User_Application.sevice.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllusers(){
      return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public User createUser(User user) throws InvalidUserException {
        if (user.getUsername() == null || user.getUsername().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new  InvalidUserException("Username and password are required");
        }
        return userRepository.save(user);
    }

}
