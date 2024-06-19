package com.example.demo.domains.scheduleEntities.activities;

import com.example.demo.domains.scheduleEntities.FinancialScheduleEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Activity extends FinancialScheduleEntity {
    @NotNull
    @JsonFormat(pattern = "MM-dd-yyyy hh:mm a", shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endDateTime;

    @Builder.Default
    private boolean allowStudentsSignupThemselves = false;

    @Size(min = 2)
    private String title;

    @Override
    public String getScheduleEntityType() {
        return "activity";
    }
    public String getActivityType() {
        return "activity";
    }
}
