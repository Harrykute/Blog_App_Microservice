package com.blogapp.userservice.controller;



import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.userservice.entity.User;
import com.blogapp.userservice.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

	private final UserService userService;

    // ✅ Constructor Injection (best practice)
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    // 🟢 CREATE User
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    // 🟡 UPDATE User
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User userPayload, @PathVariable("id") Long userId) {
        User updatedUser = userService.updateUser(userPayload, userId);
        return ResponseEntity.ok(updatedUser);
    }

    // 🔴 DELETE User
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // 🔵 GET User by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    // 🟣 GET All Users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
	
}
