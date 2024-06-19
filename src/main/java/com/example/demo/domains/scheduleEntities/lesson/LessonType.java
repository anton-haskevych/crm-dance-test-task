package com.example.demo.domains.scheduleEntities.lesson;

import com.example.demo.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LessonType extends BaseEntity {
    String type;
}
