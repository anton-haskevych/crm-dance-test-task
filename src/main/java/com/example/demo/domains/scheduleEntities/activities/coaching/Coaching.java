package com.example.demo.domains.scheduleEntities.activities.coaching;

import com.example.demo.domains.scheduleEntities.activities.Activity;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Coaching extends Activity {
    @ManyToOne
    @Setter(AccessLevel.NONE)
    @JsonIncludeProperties(value = {"id", "firstName", "lastName", "avatarKey", "email"})
    @JoinColumn(name = "coach_id")
    Coach coach;

    @Override
    public String getActivityType() {
        return "coaching";
    }

}