package com.example.demo.domains.scheduleEntities.lesson;

import com.example.demo.domains.scheduleEntities.FinancialScheduleEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;

@Entity
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
public class Lesson extends FinancialScheduleEntity {
    String lessonType;

    @Override
    public String getScheduleEntityType() {
        return "lesson";
    }

    public Lesson(ScheduleEntityBuilder<?, ?> b, String lessonType) {
        super(b);
        this.lessonType = lessonType;
    }
}