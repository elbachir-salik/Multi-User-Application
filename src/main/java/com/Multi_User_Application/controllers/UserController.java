package com.Multi_User_Application.controllers;


import com.Multi_User_Application.entities.SubscriptionPlan;
import com.Multi_User_Application.entities.User;

import com.Multi_User_Application.sevice.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('Beta_Player', 'Company_User')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAnyAuthority('Beta_Player', 'Company_User')")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/users")
    @PreAuthorize("hasAuthority('Beta_Player')")
    public ResponseEntity<User> addUser(@RequestBody User user){
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }
    @PutMapping("/user/{id}")
    @PreAuthorize("hasAuthority('Beta_Player')")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('Beta_Player')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/subscription")
    @PreAuthorize("hasAuthority('GROWTH_PLAN_SUBSCRIBER')")
    public ResponseEntity<?> getSubscriptions(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        SubscriptionPlan subscriptionPlan = user.getSubscriptionPlan();
        if (subscriptionPlan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No subscription plan assigned to this user.");
        }

        return ResponseEntity.ok(subscriptionPlan);
    }

}
