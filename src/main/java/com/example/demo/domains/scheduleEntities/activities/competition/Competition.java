package com.example.demo.domains.scheduleEntities.activities.competition;

import com.example.demo.domains.scheduleEntities.activities.Activity;
import com.example.demo.domains.users.Student;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Competition extends Activity {

    String location;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "competition", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"lessons", "authorities", "roles", "competition"})
    Set<CompetitionMember> members;

    public CompetitionMember addMember(Student student) {
        if (this.members == null) {
            this.members = new HashSet<>();
        }
        var competitionMember = CompetitionMember.builder()
                .student(student)
                .competition(this)
                .winningPlace(0)
                .build();
        this.members.add(competitionMember);
        return competitionMember;
    }

    @Override
    public String getActivityType() {
        return "competition";
    }
}