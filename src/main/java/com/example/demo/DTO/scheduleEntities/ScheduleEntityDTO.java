package com.example.demo.DTO.scheduleEntities;

import com.example.demo.DTO.users.InstructorDTO;
import com.example.demo.domains.RecurringBookingProperties;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ScheduleEntityDTO {
    private Long id;
    @NotNull
    @JsonFormat(pattern = "MM-dd-yyyy hh:mm a", shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startDateTime;
    private String comment;
    private String scheduleEntityType;
    private Set<InstructorDTO> instructors;
    @JsonIncludeProperties(value = {"id"})
    RecurringBookingProperties recurringBookingProperties;
    public String getScheduleEntityType() {
        return "scheduleEntity";
    }
}
