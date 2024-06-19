package com.example.demo.domains.scheduleEntities;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@Setter
@Getter
public class CommentBlock extends ScheduleEntity {

    @Override
    public String getScheduleEntityType() {
        return "block";
    }

}
