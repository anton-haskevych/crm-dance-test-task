package com.example.demo.domains.scheduleEntities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialScheduleEntity extends ScheduleEntity {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "financialScheduleEntity", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"financialScheduleEntity"})
    private Set<Attendance> attendance;

    @Override
    public String getScheduleEntityType() {
        return "financial";
    }

    public FinancialScheduleEntity(ScheduleEntityBuilder<?, ?> b) {
        super(b);
    }
}
