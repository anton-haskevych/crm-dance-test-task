package com.example.demo.fakeInitializers;

import com.example.demo.domains.Company;
import com.example.demo.domains.Studio;
import com.example.demo.domains.lessons.LessonType;
import com.example.demo.domains.lessons.ScheduleSlot;
import com.example.demo.domains.users.Owner;
import com.example.demo.repositories.CompanyRepository;
import com.example.demo.security.user.JwtUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Order(1)
@Slf4j
public class initAppPresetsAndUsers implements CommandLineRunner {
    @Value("${app.init-fake-data}")
    private Boolean init;
    @Autowired
    StudioRepository studioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private JwtUserService userService;
    @Autowired
    private LessonTypeRepository lessonTypeRepository;
    @Autowired
    private JwtUserService jwtUserService;

    @Override
    public void run(String... args) throws Exception {
        if (studioRepository.findAll().isEmpty()) {
            createStudiosAndCompany();
        }
        if (lessonTypeRepository.findAll().isEmpty()) {
            createLessonTypes();
            log.info("Lesson types have been initialized");
        }
    }

    public void createStudiosAndCompany() {
        Set<Studio> studios = new HashSet<>();
        var scheduleSlots = List.of(
                LocalTime.of(8, 30),
                LocalTime.of(9, 15),
                LocalTime.of(10, 0),
                LocalTime.of(10, 45),
                LocalTime.of(11, 30),
                LocalTime.of(12, 15),
                LocalTime.of(13, 0),
                LocalTime.of(13, 45),
                LocalTime.of(14, 30),
                LocalTime.of(15, 15),
                LocalTime.of(16, 0),
                LocalTime.of(16, 45),
                LocalTime.of(17, 30),
                LocalTime.of(18, 15),
                LocalTime.of(19, 0),
                LocalTime.of(19, 45),
                LocalTime.of(20, 30),
                LocalTime.of(21, 15)
        );
        var SRQ = Studio.builder()
                .studioName("Sarasota")
                .abbreviation("SRQ")
                .address("2272 Main St, Sarasota, FL 34237")
                .build();
        SRQ.setScheduleSlots(getScheduleSlotsForStudio(scheduleSlots, SRQ));
        studios.add(SRQ);
        Company company = Company.builder()
                .name("Dynasty Dance Clubs")
                .stripeConnectedAccountId("acct_1PEJTpQ9BmeOOfOz")
                .generalEmail("gaskevich06@gmail.com")
                .phoneNumber("(941) 955-8558")
                .appThemeColor("blue")
                .defaultPricePerLessonCredit(BigDecimal.valueOf(110.00))
                .build();
        var owner = Owner.builder()
                .username("mlot")
                .company(company)
//                    .firstName("Maksym")
                .firstName("Dmytro")
//                    .lastName("Lototsky")
                .lastName("Churchun")
                .email("owner@gmail.com")
                .birthday(LocalDate.now().minusYears(100))
                .password(passwordEncoder.encode("test123#"))
//                    .password("test123#")
                .enabled(true)
                .build();
        if (!init) {
            owner.setPassword(passwordEncoder.encode("#48BeaniesInMyHeart"));
        }
        var savedCompany = companyRepository.save(company);
        owner.setCompany(savedCompany);
        userService.save(owner);
        company.setOwner(Set.of(owner));
        companyRepository.save(savedCompany);
        studioRepository.saveAll(studios);
        jwtUserService.setUsersPresetsToAllPossibleStudios(owner);
        log.info("Studio(s) have been initialized");

    }

    public List<LessonType> createLessonTypes() {
        var set = new HashSet<LessonType>();
        set.add(new LessonType("Introductory"));
        set.add(new LessonType("Confidence Builder"));
        set.add(new LessonType("Social Ease"));
        set.add(new LessonType("Bronze"));
        set.add(new LessonType("Renewal"));
        set.add(new LessonType("Wedding Package"));
        return lessonTypeRepository.saveAll(set);
    }


    public Set<ScheduleSlot> getScheduleSlotsForStudio(List<LocalTime> times, Studio studio) {
        return times.stream().map(t -> new ScheduleSlot(t, studio)).collect(Collectors.toSet());
    }

}
