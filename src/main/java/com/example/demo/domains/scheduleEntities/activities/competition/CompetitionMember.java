package com.example.demo.domains.scheduleEntities.activities.competition;

import com.example.demo.BaseEntity;
import com.example.demo.domains.users.Student;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CompetitionMember extends BaseEntity{

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnoreProperties(value = {"members"})
	Competition competition;

	@ManyToOne
	@JsonIgnoreProperties(value = {"lessons", "authorities", "roles", "coachings", "competitionMember", "events", "attendance"})
	Student student;

	int winningPlace;
}
