package com.example.demo.controllers;

import com.example.demo.DTO.AddMultipleScheduleEntitiesRequestDTO;
import com.example.demo.DTO.scheduleEntities.ScheduleEntityDTO;
import com.example.demo.DTO.users.InstructorDTO;
import com.example.demo.domains.RecurringBookingProperties;
import com.example.demo.domains.scheduleEntities.lesson.Lesson;
import com.example.demo.services.InstructorService;
import com.example.demo.services.ScheduleEntitiesService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/schedule")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")
@RequiredArgsConstructor
@Controller
@Slf4j
public class ScheduleController {
    private final ScheduleEntitiesService scheduleEntitiesService;
    private final InstructorService instructorService;

    @GetMapping("/get/entities-by-date")
    public Set<ScheduleEntityDTO> getScheduleEntitiesByDate(@RequestParam String date) {
        return scheduleEntitiesService.getScheduleEntitiesByDate(date);
    }

    @GetMapping("/get/all-instructors")
    public Set<InstructorDTO> getAllInstructors() {
        return instructorService.getAll();
    }

    @PostMapping("/create/recurring-booking")
    // Make it possible to create Lesson, CommentBlock, Event using AddMultipleScheduleEntitiesRequestDTO<NEEDED_CLASS>
    public void createRecurringScheduleEntities(@RequestBody AddMultipleScheduleEntitiesRequestDTO<Lesson> body) {
        // Save all recurring entities
        // Save properties of the recurring entities
        // Livestream changes to schedule
        // Log the request
    }

    @DeleteMapping("/delete/recurring-booking")
    public void deleteRecurringBookingPropertiesById(@RequestParam Long recurringBookingId) {
        // Delete all recurring entities
        // Delete properties of the recurring entities
        // Livestream changes to schedule
        // Log the request
    }

    @GetMapping("/get/recurring-booking")
    public RecurringBookingProperties getRecurringBookingPropertiesByEntityId(@RequestParam Long ID) {
        // Get properties of the recurring lessons ()
    }

}
