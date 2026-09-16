package com.example.tskmngr.controllers;

import com.example.tskmngr.dto.RegisterRequest;
import com.example.tskmngr.dto.UserDTO;
import com.example.tskmngr.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/registration")
    public UserDTO registration(@RequestBody RegisterRequest request) {
        return userService.saveUser(request);
    }

}
