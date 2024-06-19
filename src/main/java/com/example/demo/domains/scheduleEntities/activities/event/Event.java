package com.example.demo.domains.scheduleEntities.activities.event;

import com.example.demo.domains.scheduleEntities.activities.Activity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "event_table")
public class Event extends Activity {

    @Override
    public String getActivityType() {
        return "event";
    }
}
