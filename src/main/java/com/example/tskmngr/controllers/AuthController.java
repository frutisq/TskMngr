package com.example.tskmngr.controllers;

import com.example.tskmngr.dto.JwtAuthentificationDto;
import com.example.tskmngr.dto.RegisterRequest;
import com.example.tskmngr.dto.UserDTO;
import com.example.tskmngr.security.JwtService;
import com.example.tskmngr.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/registration")
    public UserDTO registration(@RequestBody RegisterRequest request) {
        System.out.println("REGISTRATION CONTROLLER REACHED");
        return userService.saveUser(request);
    }

    @PostMapping("/login")
    public JwtAuthentificationDto login(@RequestBody RegisterRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        authenticationManager.authenticate(authenticationToken);
        return jwtService.generateAuthToken(request.getUsername());
    }


}
