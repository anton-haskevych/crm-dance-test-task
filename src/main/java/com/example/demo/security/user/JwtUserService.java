package com.example.demo.security.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtUserService {

    private final JwtUserRepository jwtUserRepository;

    @Value("${bucket.name}")
    String bucketName;

    private final PasswordEncoder passwordEncoder;


    public <T extends JwtUser> T save(T user) {
        try {
            return jwtUserRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends JwtUser> T create(T user) throws Exception {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUsername(user.getId() + user.getFirstName() + "_" + user.getLastName());
        return save(user);
    }

    public JwtUser getByEmail(String email) {
        return jwtUserRepository.findJwtUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public JwtUser getByUsername(String username) {
        return jwtUserRepository.findJwtUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public JwtUser getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return getByUsername(authentication.getPrincipal().toString());
    }

}
