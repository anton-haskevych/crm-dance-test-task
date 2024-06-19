package com.example.demo.DTO.users;

import com.example.demo.domains.users.Instructor;
import com.example.demo.domains.users.Teacher;
import com.example.demo.security.user.Position;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@ToString
public class InstructorDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Position position;
    private Integer lessonCount = 0;
    private Integer lessonsThisWeek = 0;
    private Integer lessonsToday = 0;

    // TODO define lessons today and lessonsThisWeek using
    /*@Formula(value = "(SELECT COUNT(*) FROM ...)")
    private Integer lessonsThisWeek;

    @Formula("(SELECT COUNT(*)\n" +
            "FROM lesson pl\n" +
            "         JOIN financial_schedule_entity fse on fse.id = pl.id\n" +
            "         JOIN schedule_entity se on se.id = fse.id\n" +
            "         JOIN schedule_entity_instructors sei on fse.id = sei.schedule_entity_id\n" +
            "WHERE sei.instructor_id = id\n" +
            "  AND DATE(se.start_date_time) = CURDATE())")
    private Integer lessonsToday;*/


    public InstructorDTO(Long id, String firstName, String lastName, String email, Position position, Integer lessonCount, Integer lessonsThisWeek, Integer lessonsToday) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.position = position;
        this.lessonCount = lessonCount;
        this.lessonsThisWeek = lessonsThisWeek;
        this.lessonsToday = lessonsToday;
    }

    public InstructorDTO(Instructor i) {
        this.id = i.getId();
        this.firstName = i.getFirstName();
        this.lastName = i.getLastName();
        this.email = i.getEmail();
        this.position = i.getPosition();
        if (i instanceof Teacher t) {
            this.lessonCount = t.getLessonCount();
            this.lessonsThisWeek = t.getLessonsThisWeek();
            this.lessonsToday = t.getLessonsToday();
        }
    }

    public InstructorDTO(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @JsonCreator
    public InstructorDTO(
            @JsonProperty("id") Long id,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("email") String email,
            @JsonProperty("lessonCount") int lessonCount,
            @JsonProperty("lessonsThisWeek") int lessonsThisWeek,
            @JsonProperty("lessonsToday") int lessonsToday) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.lessonCount = lessonCount;
        this.lessonsThisWeek = lessonsThisWeek;
        this.lessonsToday = lessonsToday;
    }


}
