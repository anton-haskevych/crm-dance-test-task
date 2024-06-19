package com.example.demo.DTO;

import com.example.demo.domains.scheduleEntities.ScheduleEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class AddMultipleScheduleEntitiesRequestDTO<T extends ScheduleEntity> {
    private final T scheduleEntity;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm a")
    private final Set<LocalTime> times;
    private final Set<Integer> daysIndexes; // from 1 to 7
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate startBound;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate endBound;
    private final String comment;

    public AddMultipleScheduleEntitiesRequestDTO(T scheduleEntity, Set<String> times, Set<Integer> daysIndexes, LocalDate startBound, LocalDate endBound, String comment) {
        this.scheduleEntity = scheduleEntity;
        this.times = times.stream().map(timeSlot -> LocalTime.parse(timeSlot, DateTimeFormatter.ofPattern("hh:mm a"))).collect(Collectors.toSet());
        this.daysIndexes = daysIndexes;
        this.startBound = startBound;
        this.endBound = endBound;
        this.comment = comment;
    }

    public String getDate() {
        return this.scheduleEntity.getDate();
    }

}
