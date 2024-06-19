package com.example.demo.domains;

import com.example.demo.BaseEntity;
import com.example.demo.domains.scheduleEntities.ScheduleEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RecurringBookingProperties extends BaseEntity {
    // Indexes of days of week from 1 to 7
    @ElementCollection
    @CollectionTable(name = "recurring_booking_properties_days_indexes", joinColumns = @JoinColumn(name = "recurring_booking_properties_id"))
    @Column(name = "day_index")
    private Set<Integer> daysIndexes;
    @ElementCollection
    @CollectionTable(name = "recurring_booking_properties_times", joinColumns = @JoinColumn(name = "recurring_booking_properties_id"))
    @Column(name = "time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm a")
    private Set<LocalTime> times;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startBound;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endBound;
    @OneToMany(mappedBy = "recurringBookingProperties", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private Set<ScheduleEntity> scheduleEntities;
    private String comment;

//        @Formula("(select count(sl.id) from standing_appointment_specs_lessons sl where sl.standing_appointment_specs_id = id)")
//    private Integer lessonCount;
}






