package com.example.demo.fakeInitializers;

import com.example.demo.domains.users.Receptionist;
import com.example.demo.domains.users.Teacher;
import com.example.demo.repositories.JwtUserRepository;
import com.example.demo.security.user.JwtUserService;
import com.example.demo.services.LessonService;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
@Order(2)
@Slf4j
public class initStaff implements CommandLineRunner {

    @Value("${app.init-fake-data}")
    private Boolean init;
    private static final int NUM_THREADS = Thread.activeCount();

    @Autowired
    LessonService lessonService;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    LessonsRepository lessonsRepository;
    @Autowired
    JwtUserRepository userRepository;
    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    LessonTypeRepository lessonTypeRepository;
    @Autowired
    StudioRepository studioRepository;
    @Autowired
    JwtUserService jwtUserService;
    @Autowired
    ReceptionistRepository receptionistRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) {
        if (init && receptionistRepository.findAll().isEmpty()) {
            init();
            var allUsers = userRepository.findAll();
            allUsers.forEach(u -> {
                try {
                    jwtUserService.setUsersPresetsToAllPossibleStudios(u);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            log.info("Staff has been initialized");
        }
    }

    public void init() {
        var random = new Random();
        // // users
        Faker faker = new Faker();
        var studios = studioRepository.findAll();
        // teacher
        Runnable initTeachers = () -> {
            var firstName = faker.name().firstName();
            var lastName = faker.name().lastName();
            Teacher user = Teacher.builder()
                    .username(faker.name().username())
                    .firstName(firstName)
                    .lastName(lastName)
                    .email("%s.%s@gmail.kom".formatted(firstName, lastName))
                    .password(passwordEncoder.encode("test123#"))
                    .birthday(LocalDate.now().minusDays(random.nextInt(30)).minusMonths(random.nextInt(12)).minusYears(random.nextInt(40) + 10))
                    .build();
            var studioSelection = random.nextInt(0, studios.size());
            if (studioSelection == studios.size()) {
                user.setStudios(new HashSet<>(studios));
            } else {
                user.setStudios(Set.of(studios.get(studioSelection)));
            }
            try {
                jwtUserService.create(user);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        executeInMultiThread(3, initTeachers);

        Runnable initReceptionists = () -> {
            var firstName = faker.name().firstName();
            var lastName = faker.name().lastName();
            var user = Receptionist.builder()
                    .username(faker.name().username())
                    .firstName(firstName)
                    .lastName(lastName)
                    .email("%s.%s@gmail.kom".formatted(firstName, lastName))
                    .password(passwordEncoder.encode("test123#"))
                    .birthday(LocalDate.now().minusDays(random.nextInt(30)).minusMonths(random.nextInt(12)).minusYears(random.nextInt(40) + 10))
                    .build();

            var studioSelection = random.nextInt(0, studios.size());
            if (studioSelection == studios.size()) {
                user.setStudios(new HashSet<>(studios));
            } else {
                user.setStudios(Set.of(studios.get(studioSelection)));
            }
            try {
                jwtUserService.create(user);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        executeInMultiThread(3, initReceptionists);
    }


    public void executeInMultiThread(int iterations, Runnable runnable) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        CountDownLatch latch = new CountDownLatch(iterations); // Number of iterations
        for (int i = 0; i < iterations; i++) {
            executorService.submit(() -> {
                try {
                    runnable.run();
                } finally {
                    latch.countDown();
                }
            });
        }
        try {
            latch.await(); // Wait for all iterations to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
