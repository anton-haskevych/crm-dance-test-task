package com.example.demo.fakeInitializers;

import com.example.demo.domains.users.Receptionist;
import com.example.demo.domains.users.Student.Student;
import com.example.demo.domains.users.Student.accounts.*;
import com.example.demo.repositories.AccountTypeRepository;
import com.example.demo.repositories.JwtUserRepository;
import com.example.demo.security.user.JwtUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
@Order(6)
@Slf4j
public class initStudentAccounts implements CommandLineRunner {

    @Value("${app.init-fake-data}")
    private Boolean init;
    private static final int NUM_THREADS = Thread.activeCount();

    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    JwtUserRepository userRepository;
    @Autowired
    LessonTypeRepository lessonTypeRepository;
    @Autowired
    JwtUserService jwtUserService;
    @Autowired
    private ReceptionistRepository receptionistRepository;
    @Autowired
    private AccountTypeRepository accountTypeRepository;
    @Autowired
    private AccountRepository accountRepository;


    @Override
    public void run(String... args) {
        // Lesson credits is created automatically in migration
        if (init && accountRepository.count() < 1) {
            init();
            log.info("Students have been initialized");
        }
    }

    public void init() {
        List<Runnable> studentTasks = new ArrayList<>();
        var random = new Random();
        var students = studentRepository.findAll();
        var accountTypes = accountTypeRepository.findAll();
        var receptionists = receptionistRepository.findAll();
        for (Student student : students) {
            studentTasks.add(() -> {
                try {
                    ArrayList<AccountType> copyAccountTypes = new ArrayList<AccountType>(accountTypes);
                    for (AccountType a : copyAccountTypes) {
                        var account = Account.builder()
                                .student(student)
                                .accountType(a)
                                .build();
//                        if (random.nextBoolean()) {
                            account = fillWithTransactions(random, account, receptionists);
//                        }
                        accountRepository.save(account);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            });
        }
        executeInMultiThread(studentTasks);
    }

    public Account fillWithTransactions(Random random, Account account, List<Receptionist> receptionists) {
        Set<Transaction> transactions = new HashSet<>();
        for (int i = 0; i < random.nextInt(0, 20); i++) {
            Transaction transaction = new Transaction(
                    receptionists.get(random.nextInt(0, receptionists.size())),
                    BigDecimal.valueOf(random.nextFloat(48.0f, 480.0f)).setScale(2, RoundingMode.CEILING),
                    BigDecimal.valueOf(random.nextFloat(1.0f, 6.0f)).setScale(1, RoundingMode.CEILING),
                    "",
                    account
            );
            setPayments(random, transaction);
            transactions.add(transaction);
        }
        account.setTransactions(transactions);
        return account;
    }

    public void setPayments(Random random, Transaction transaction) {
        Set<Payment> payments = new LinkedHashSet<>();
        int paymentsToCreate = random.nextInt(2, 7);
        for (int j = 0; j < paymentsToCreate; j++) {
            if (random.nextBoolean()) {
                Payment payment = new Payment();
                payment.setTransactionType(TransactionType.BalanceChange);
                payment.setTransaction(transaction);
                payment.setPaymentType(PaymentType.Other);
                payment.setValue(transaction.getTotalMonetaryValue().divide(BigDecimal.valueOf(paymentsToCreate), 2, RoundingMode.FLOOR));
                payments.add(payment);
            }
        }
        transaction.setPayments(payments);
    }

    public void executeInMultiThread(List<Runnable> tasks) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        CountDownLatch latch = new CountDownLatch(tasks.size());

        for (Runnable task : tasks) {
            executorService.submit(() -> {
                try {
                    task.run();
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }


}
