package com.example.demo.services;

import com.example.demo.DTO.scheduleEntities.ScheduleEntityDTO;
import com.example.demo.domains.scheduleEntities.activities.Activity;
import com.example.demo.domains.scheduleEntities.activities.coaching.Coaching;
import com.example.demo.domains.scheduleEntities.lesson.Lesson;
import com.example.demo.domains.scheduleEntities.CommentBlock;
import com.example.demo.domains.scheduleEntities.FinancialScheduleEntity;
import com.example.demo.domains.scheduleEntities.ScheduleEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleEntitiesService {
    private final ScheduleEntityRepository scheduleEntityRepository;

    public <T extends FinancialScheduleEntity> T save(T entity) {
        boolean exists = scheduleEntityRepository.existsByStartDateTimeAndInstructors(entity.getStartDateTime(), entity.getInstructors());
        if (exists) {
            throw new IllegalStateException("Entity with the same startDateTime and instructors already exists");
        }
        return scheduleEntityRepository.saveAndFlush(entity);
    }

    @Transactional
    public Set<ScheduleEntityDTO> getScheduleEntitiesByDate(String date) {
        LocalDate localDate = LocalDate.parse(date);
        LocalDateTime from = LocalDateTime.of(localDate, LocalTime.MIN);
        LocalDateTime until = LocalDateTime.of(localDate, LocalTime.MAX);
        Set<ScheduleEntity> scheduleEntities = scheduleEntityRepository.getByStartDateTimeBetween(from, until);
        return scheduleEntities.stream().map(this::convertToDTO).collect(Collectors.toSet());
    }

    public ScheduleEntityDTO convertToDTO(ScheduleEntity entity) {
        // TODO Handle conversion
        if (entity instanceof Lesson lesson) {
        } else if (entity instanceof Coaching coaching) {
        } else if (entity instanceof Activity activity) {
        } else if (entity instanceof CommentBlock commentBlock) {
        } else {
            throw new IllegalStateException("Unknown ScheduleEntity type: " + entity.getClass());
        }
    }

}
