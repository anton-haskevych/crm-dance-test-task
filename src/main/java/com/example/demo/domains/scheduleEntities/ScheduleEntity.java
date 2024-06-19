package com.example.demo.domains.scheduleEntities;

import com.example.demo.domains.RecurringBookingProperties;
import com.example.demo.domains.users.Instructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    @JsonFormat(pattern = "MM-dd-yyyy hh:mm a", shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startDateTime;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "schedule_entity_instructors",
            joinColumns = @JoinColumn(name = "schedule_entity_id"),
            inverseJoinColumns = @JoinColumn(name = "instructor_id"))
    @JsonIncludeProperties(value = {"id", "firstName", "lastName"})
    private Set<Instructor> instructors;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @JsonIncludeProperties(value = {"id"})
    @ManyToOne(fetch = FetchType.LAZY)
    private RecurringBookingProperties recurringBookingProperties;

    public String getScheduleEntityType() {
        return "scheduleEntity";
    }

    public String getDate(){
        return startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}