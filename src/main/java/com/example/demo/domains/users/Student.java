package com.example.demo.domains.users;

import com.example.demo.domains.scheduleEntities.Attendance;
import com.example.demo.security.user.JwtUser;
import com.example.demo.security.user.Position;
import com.example.demo.security.user.Role;
import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(value = {"role", "canceledLessons", "authorities", "lessons", "events", "lessonCredits", "coachings", "competitionMember", "attendance"}, allowGetters = true)
public class Student extends JwtUser {
    {
        this.setRole(Set.of(Role.ROLE_USER));
        this.setPosition(Position.Student);
    }

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"student"})
    @JsonIgnore
    private Set<Attendance> attendance;

    public Student(Long id) {
        super(id);
    }

    public Student(JwtUser jwtUser) {
        super(jwtUser);
    }

    private String comment;

}
