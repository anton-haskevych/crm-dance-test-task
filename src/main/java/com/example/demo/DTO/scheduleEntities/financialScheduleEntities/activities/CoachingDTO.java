package com.example.demo.DTO.scheduleEntities.financialScheduleEntities.activities;

import com.example.demo.DTO.users.UserBasicInfoDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoachingDTO extends ActivityDTO {
    private UserBasicInfoDTO coach;
}
