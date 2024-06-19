package com.example.demo.domains.scheduleEntities;

import com.example.demo.domains.users.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "financial_schedule_entity_id")
    @JsonIgnore
    private FinancialScheduleEntity financialScheduleEntity;

    @ManyToOne
    @JsonIncludeProperties(value = {"lessonCredits", "firstName", "lastName", "id"})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Student student;

    @JsonProperty("isAttended")
    private boolean isAttended;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attendance that)) return false;

        if (isAttended() != that.isAttended()) return false;
        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        return getStudent() != null ? getStudent().equals(that.getStudent()) : that.getStudent() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getStudent() != null ? getStudent().hashCode() : 0);
        result = 31 * result + (isAttended() ? 1 : 0);
        return result;
    }
}
