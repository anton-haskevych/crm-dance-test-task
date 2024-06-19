package com.example.demo.security.services;

import com.example.demo.security.user.JwtUser;
import com.example.demo.security.user.JwtUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final JwtUserService jwtUserService;

    public String authenticate(String email, String password, String companyId) throws RuntimeException {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(email, password);
            Authentication authentication = authenticationManager.authenticate(authToken);
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            JwtUser user = jwtUserService.getByUsername(principal.getUsername());
            return tokenService.generateToken(user, companyId);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid User or Password");
        }
    }

}
