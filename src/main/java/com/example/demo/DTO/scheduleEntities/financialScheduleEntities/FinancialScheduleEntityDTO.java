package com.example.demo.DTO.scheduleEntities.financialScheduleEntities;

import com.example.demo.DTO.scheduleEntities.ScheduleEntityDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FinancialScheduleEntityDTO extends ScheduleEntityDTO {
    private Set<AttendanceDTO> attendance;
}
