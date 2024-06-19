package com.example.demo.DTO.scheduleEntities.financialScheduleEntities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LessonDTO extends FinancialScheduleEntityDTO {
    String lessonType;
    boolean isStandingAppointment = false;

    public String getScheduleEntityType() {
        return "lesson";
    }
}
