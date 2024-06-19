package com.example.demo.security.controllers;

import com.example.demo.security.LoginCredentials;
import com.example.demo.security.services.AuthenticationService;
import com.example.demo.security.user.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final JwtUserService userService;
    private final AuthenticationService authenticationService;


    @GetMapping("/get-myself")
    public JwtUser getMyself() {
        return userService.getCurrentUser();
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody @Valid LoginCredentials request) {
        return Map.of("token", authenticationService.authenticate(request.getEmail(),
                request.getPassword(), request.getCompanyId()));
    }

}
















