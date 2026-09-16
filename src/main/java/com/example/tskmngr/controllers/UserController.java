package com.example.tskmngr.controllers;

import com.example.tskmngr.dto.RegisterRequest;
import com.example.tskmngr.dto.UserDTO;
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
    public UserDTO createUser(@RequestBody RegisterRequest request) {
        return userService.saveUser(request);
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable Long userId) {
        return userService.findById(userId);
    }

    @PutMapping("/{userId}")
    public UserDTO updateUser(@PathVariable Long userId, @RequestBody UserDTO dto) {
        return userService.update(userId, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

}
