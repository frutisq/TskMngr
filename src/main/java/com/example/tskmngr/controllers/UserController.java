package com.example.tskmngr.controllers;

import com.example.tskmngr.entity.User;
import com.example.tskmngr.repository.UserRepository;
import com.example.tskmngr.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return userService.findById(userId);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId,@RequestBody User userDetails){
        return userService.update(userId, userDetails);
    }



}
