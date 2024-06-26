package com.example.demo.fakeInitializers;

import com.example.demo.domains.Studio;
import com.example.demo.domains.lessons.*;
import com.example.demo.domains.users.Instructor;
import com.example.demo.domains.users.Student.Student;
import com.example.demo.domains.users.Student.accounts.AccountType;
import com.example.demo.repositories.AccountTypeRepository;
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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
@Order(3)
@Slf4j
public class initLessonsAndStudents implements CommandLineRunner {

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

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private ReceptionistRepository receptionistRepository;
    @Autowired
    private AccountTypeRepository accountTypeRepository;
    @Autowired
    private InstructorRepository instructorRepository;


    @Override
    public void run(String... args) {
        if (accountTypeRepository.count() <= 1) {
            initAccountTypes();
            log.info("Account types have been initialized");
        }
        if (init && lessonsRepository.findAll().isEmpty()) {
            init();
            log.info("Lessons have been initialized");
        }
    }

    public void init() {
        var random = new Random();
        var allUsers = userRepository.findAll();
        var receptionists = receptionistRepository.findAll();

        allUsers.forEach(u -> {
            try {
                jwtUserService.setUsersPresetsToAllPossibleStudios(u);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        // // users
        Faker faker = new Faker();
        var studios = studioRepository.findAll();

        // students
        var teachers = instructorRepository.findAll();
        Runnable initStudents = () -> {
            var firstName = faker.name().firstName();
            var lastName = faker.name().lastName();
            var localDate = LocalDate.now().minusMonths(random.nextInt(0, 12)).minusDays(random.nextInt(0, 30));
            var user = Student.builder()
                    .studio(studios.get(random.nextInt(0, studios.size())))
                    .instructors(getRandomTeachersSet(teachers, random))
                    .username(faker.name().username())
                    .firstName(firstName)
                    .lastName(lastName)
                    .registered(Timestamp.valueOf(LocalDateTime.of(localDate, LocalTime.now())))
                    .email("%s.%s@gmail.kom".formatted(firstName, lastName))
                    .phoneNumber(faker.phoneNumber().phoneNumber())
                    .password(passwordEncoder.encode("test123#"))
                    .birthday(LocalDate.now().minusDays(random.nextInt(30)).minusMonths(random.nextInt(12)).minusYears(random.nextInt(40) + 10))
                    .homeAddress(faker.address().fullAddress())
                    .build();
            try {
                jwtUserService.create(user);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        executeInMultiThread(100, initStudents);

        // lessons
        var studio = studios.get(random.nextInt(0, 1));
//        var studio = studios.get(random.nextInt(0, 2));
        var accountToCharge = accountTypeRepository.findById(2L).orElseThrow();
        var lessonTimes = studio.getScheduleSlots();
        List<ScheduleSlot> lessonTimesList = new ArrayList<>(lessonTimes);
        var students = studentRepository.findAll();
        Runnable initLessons = () -> {
            var randomTime = lessonTimesList.get(random.nextInt(0, lessonTimes.size()));
            var randomTeacher = teachers.get(random.nextInt(0, teachers.size()));
            var randomStudent = students.get(random.nextInt(0, students.size()));
//            var randomStudent = students.get(1);
            var localDate = LocalDate.now().minusMonths(random.nextInt(0, 2)).plusMonths(random.nextInt(0, 6)).minusDays(random.nextInt(0, 30)).plusDays(random.nextInt(0, 30));
            planLesson(randomStudent, randomTeacher, localDate, randomTime.getTime(), studio, accountToCharge);
        };
        executeInMultiThread(3000, initLessons);
    }


    public void executeInMultiThread(int iterations, Runnable runnable) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        CountDownLatch latch = new CountDownLatch(iterations); // Number of iterations
        for (int i = 0; i < iterations; i++) {
//            final int iteration = i; // Variable needs to be effectively final to use inside the lambda expression
            executorService.submit(() -> {
                try {
                    runnable.run();
//                    System.out.println("Iteration " + iteration + " completed.");
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

    public void planLesson(Student student, Instructor instructor, LocalDate date, LocalTime time, Studio studio, AccountType accountType) {
        // build lesson
        Lesson lesson = Lesson.builder()
                .studio(studio)
                .instructors(Set.of(instructor))
                .startDateTime(LocalDateTime.of(date, time))
                .cashChargeValue(BigDecimal.valueOf(40.50f))
                .accountTypeToCharge(accountType)
                .creditsToCharge(BigDecimal.ONE)
                .build();
        try {
            lesson.setAttendance(Set.of(Attendance.builder()
                    .student(student)
                    .financialScheduleEntity(lesson)
                    .build()));
        } catch (NullPointerException e) {
            lesson.setAttendance(new HashSet<>());
        }
//        var checkedLesson = lessonService.detectAndSetType(lesson);
        lessonsRepository.save(lesson);
    }

    public Set<Instructor> getRandomTeachersSet(List<Instructor> teachers, Random random) {
        Set<Instructor> selectedTeachers = new HashSet<>();
        for (Instructor teacher : teachers) {
            if (random.nextBoolean()) {
                selectedTeachers.add(teacher);
            }
        }
        return selectedTeachers;
    }

    public void initAccountTypes() {
//- Lessons - “L” for Private Lessons
//- Groups - “G” for Group Classes
//- Coachings - “OC” for Outside Coachings & “IC” for In House Coachings
//- In Studio Events - “P” for Parties, “MM” for Mini Matches, “LP” for Level Passings, “PR” for Practice Rounds, “SC” for Showcases
//- Outside Events - “NO” for Night Out, “O” for Other
//- Competitions -  None for now. Not 100% sure how we’ll do these yet.
//- Scholarship - “SL” for Scholarship Lesson, “SG” for Scholarship Group
//- Merchandise - No credit types. We just need it to track products sold.

        var accountTypes = List.of(
                AccountType.builder()
                        .title("Groups")
                        .abbreviation("G")
                        .isCreditBasedCharge(true)
                        .build(),
                AccountType.builder()
                        .title("Coachings")
                        .abbreviation("CX")
                        .isCreditBasedCharge(true)
                        .build(),
                AccountType.builder()
                        .title("In Studio Events")
                        .abbreviation("ISE")
                        .isCreditBasedCharge(true)
                        .build(),
                AccountType.builder()
                        .title("Outside Events")
                        .abbreviation("NO")
                        .isCreditBasedCharge(true)
                        .build(),
                AccountType.builder()
                        .title("Competitions")
                        .abbreviation("CMP")
                        .isCreditBasedCharge(true)
                        .build(),
                AccountType.builder()
                        .title("Scholarship")
                        .abbreviation("SL")
                        .isCreditBasedCharge(true)
                        .build(),
                AccountType.builder()
                        .title("Merchandise")
                        .abbreviation("G")
                        .isCreditBasedCharge(false)
                        .build());
        accountTypeRepository.saveAll(accountTypes);
    }
}
