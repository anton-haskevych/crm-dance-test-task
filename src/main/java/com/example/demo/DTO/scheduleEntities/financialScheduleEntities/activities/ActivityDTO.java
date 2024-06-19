package com.example.demo.DTO.scheduleEntities.financialScheduleEntities.activities;

import com.example.demo.DTO.scheduleEntities.financialScheduleEntities.FinancialScheduleEntityDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDTO extends FinancialScheduleEntityDTO {
    @NotNull
    @JsonFormat(pattern = "MM-dd-yyyy HH:mm", shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endDateTime;
    private boolean allowStudentsSignupThemselves;
    private String title;
    private String activityType;
    private String scheduleEntityType;
    public String getScheduleEntityType() {
        return "activity";
    }
}
