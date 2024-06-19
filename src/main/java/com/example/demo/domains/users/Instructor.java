package com.example.demo.domains.users;

import com.example.demo.security.user.JwtUser;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Instructor extends JwtUser {
    public Instructor(JwtUser jwtUser) {
        super(jwtUser);
    }

    public Instructor(Long id) {
        super(id);
    }
}
