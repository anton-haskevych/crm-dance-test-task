package com.example.demo.DTO.scheduleEntities.financialScheduleEntities;

import com.example.demo.DTO.users.UserBasicInfoDTO;
import com.example.demo.domains.scheduleEntities.FinancialScheduleEntity;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO {
    private Long id;
    @JsonIncludeProperties(value = {"id"})
    private FinancialScheduleEntity financialScheduleEntity;
    private UserBasicInfoDTO student;
    private boolean isAttended;
}
