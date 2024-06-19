package com.example.demo.domains.users;

import com.example.demo.security.user.JwtUser;
import com.example.demo.security.user.Position;
import com.example.demo.security.user.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Owner extends Instructor {

    {
        this.setRole(Set.of(Role.ROLE_USER,
                Role.ROLE_ADMIN,
                Role.ROLE_SUPER_ADMIN,
                Role.ROLE_OWNER));
        this.setPosition(Position.Owner);
    }

    public Owner(JwtUser jwtUser) {
        super(jwtUser);
    }
}