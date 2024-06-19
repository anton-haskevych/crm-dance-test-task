package com.example.demo.services;

import com.example.demo.DTO.PageDTO;
import com.example.demo.DTO.scheduleEntities.financialScheduleEntities.activities.ActivityDTO;
import com.example.demo.DTO.scheduleEntities.financialScheduleEntities.activities.CoachingDTO;
import com.example.demo.DTO.users.UserBasicInfoDTO;
import com.example.demo.domains.scheduleEntities.activities.Activity;
import com.example.demo.domains.scheduleEntities.activities.coaching.Coaching;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ActivitiesService {

    private final ActivitiesRepository activitiesRepository;

    public PageDTO<ActivityDTO> getAllUpcomingActivities(@RequestParam(name = "pageNumber") int pageNumber,
                                                         @RequestParam(name = "pageSize") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Activity> activitiesPage = activitiesRepository.findUpcomingActivities(pageable);
        Page<ActivityDTO> activitiesDTOPage = activitiesPage.map(entity -> {
            if (entity instanceof Coaching coaching) {
                return new CoachingDTO(coaching, new UserBasicInfoDTO(coaching.getCoach()));
            } else {
                return new ActivityDTO(entity);
            }
        });
        return PageDTO.fromPage(activitiesDTOPage);
    }

}
