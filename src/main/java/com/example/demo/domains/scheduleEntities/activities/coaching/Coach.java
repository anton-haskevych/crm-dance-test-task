package com.example.demo.domains.scheduleEntities.activities.coaching;

import com.example.demo.security.user.JwtUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coaches")
@Getter
@Setter
@SuperBuilder
public class Coach extends JwtUser {

    {
        this.setRole(Set.of());
        this.setPosition(null);
        this.setEnabled(false);
    }

    @OneToMany(mappedBy = "coach", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Coaching> coachings;

}