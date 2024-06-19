package com.example.demo.services;

import com.example.demo.DTO.users.InstructorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final JdbcTemplate jdbcTemplate;

    public Set<InstructorDTO> getAll() {
        var instructors = instructorRepository.findAll();

        // lessonsThisWeek
        //        String sql2 = "SELECT COUNT(*) FROM ... id = ? AND YEARWEEK(se.start_date_time, 1) = YEARWEEK(CURDATE(), 1)";

        // lessonsToday
        //        String sql3 = "SELECT COUNT(*) FROM ... id = ? AND DATE(se.start_date_time) = CURDATE()";


        return instructors.stream().map(i -> new InstructorDTO(i.getId(), i.getFirstName(), i.getLastName(), i.getEmail(), i.getPosition(),
                jdbcTemplate.queryForObject(sql1, new Object[]{i.getId()}, Integer.class),
                jdbcTemplate.queryForObject(sql2, new Object[]{i.getId()}, Integer.class),
                jdbcTemplate.queryForObject(sql3, new Object[]{i.getId()}, Integer.class)
        )).collect(Collectors.toSet());
    }
}
