package com.example.demo.fakeInitializers;

import com.example.demo.domains.Studio;
import com.example.demo.domains.activities.event.Event;
import com.example.demo.domains.users.Instructor;
import com.example.demo.domains.users.Student.Student;
import com.example.demo.repositories.AccountTypeRepository;
import com.example.demo.services.CompetitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Order(4)
@Slf4j
public class initActivities implements CommandLineRunner {
    @Value("${app.init-fake-data}")
    private Boolean init;
    private final InstructorRepository instructorRepository;
    private final EventRepository eventRepository;
    private final StudentRepository studentRepository;
    private final StudioRepository studioRepository;
    private final AccountTypeRepository accountTypeRepository;

    @Override
    public void run(String... args) {
        if (init && eventRepository.findAll().isEmpty()) {
            Random random = new Random();
            List<Studio> studios = studioRepository.findAll();
            List<Instructor> teachers = instructorRepository.findAll();
            List<Student> students = studentRepository.findAll();
            // Creating events
            createEvent("Latin Dance Competition Finals", "Witness the thrilling finals of our Latin dance competition series!", studios, teachers.get(1), students, random);
            createEvent("Street Dance Showdown", "Experience the energy of street dance in this epic showdown between our top dancers!", studios, teachers.get(2), students, random);
            createEvent("Contemporary Dance Workshop", "Explore the art of contemporary dance in this inspiring workshop.", studios, teachers.get(3), students, random);
            createEvent("Ballet Performance Showcase", "Join us for an enchanting evening of grace and elegance as our ballet dancers take the stage.", studios, teachers.get(4), students, random);
            createEvent("Jazz Dance Masterclass", "Learn the fundamentals of jazz dance from our experienced instructors in this intensive masterclass.", studios, teachers.get(1), students, random);
            createEvent("Salsa Competition Showcase", "Watch our talented dancers compete in an electrifying salsa competition!", studios, teachers.get(0), students, random);
            log.info("Activities have been initialized");
        }
    }

    private void createEvent(String title, String comment, List<Studio> studios, Instructor teacher, List<Student> students, Random random) {
        LocalDateTime startDateTime = LocalDateTime.now().plusDays(random.nextInt(40)).plusHours(24).plusMinutes(300);
        Event event = Event.builder()
                .title(title)
                .studio(studios.get(random.nextInt(0, studios.size())))
                .instructors(Set.of(teacher))
                .comment(comment)
                .startDateTime(startDateTime)
                .allowStudentsSignupThemselves(random.nextBoolean())
                .endDateTime(startDateTime.plusHours(2))
                .accountTypeToCharge(accountTypeRepository.findById(2L).orElseThrow())
                .cashChargeValue(BigDecimal.TEN)
                .creditsToCharge(BigDecimal.ONE)
                .build();
        // Randomly select students to attend the event
        List<Student> selectedStudents = selectRandomStudents(students, random);
        selectedStudents.forEach(event::addStudent);
        eventRepository.save(event);
    }

    private List<Student> selectRandomStudents(List<Student> students, Random random) {
        int numStudentsToSelect = random.nextInt(0, students.size() / 2); // Randomly select at least one student
        Collections.shuffle(students); // Shuffle the list to randomize student selection
        return students.subList(0, numStudentsToSelect);
    }

}