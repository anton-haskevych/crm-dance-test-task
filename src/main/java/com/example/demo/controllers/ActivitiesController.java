package com.example.demo.controllers;

import com.example.demo.DTO.scheduleEntities.financialScheduleEntities.activities.ActivityDTO;
import com.example.demo.DTO.PageDTO;
import com.example.demo.services.ActivitiesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activities")
@RequiredArgsConstructor
@Slf4j
public class ActivitiesController {
    private final ActivitiesService activitiesService;

    @GetMapping("/get-upcoming")
    public PageDTO<ActivityDTO> getAllUpcomingActivities(@RequestParam(name = "pageNumber") int pageNumber,
                                                         @RequestParam(name = "pageSize") int pageSize) {
        return activitiesService.getAllUpcomingActivities(pageNumber, pageSize);
    }

}
