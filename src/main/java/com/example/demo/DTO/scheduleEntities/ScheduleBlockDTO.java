package com.example.demo.DTO.scheduleEntities;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ScheduleBlockDTO extends ScheduleEntityDTO{
    @Override
    public String getScheduleEntityType() {
        return "block";
    }
}
