package com.example.demo.domains.users;

import com.example.demo.security.user.JwtUser;
import com.example.demo.security.user.Position;
import com.example.demo.security.user.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Formula;

import jakarta.persistence.*;

import java.util.Set;

@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "teachers")
@Getter
@Setter
public class Teacher extends Instructor {

    {
        this.setRole(Set.of(Role.ROLE_ADMIN, Role.ROLE_USER));
        this.setPosition(Position.Teacher);
    }

    public Teacher(JwtUser jwtUser) {
        super(jwtUser);
    }

    public Teacher(Long teacherId) {
        super(teacherId);
    }

}