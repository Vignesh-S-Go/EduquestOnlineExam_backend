// DataInitializer.java - Creates sample data for testing
package com.vip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("unused")
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ExamRepository examRepository;
    
    @Autowired
    private ExamRegistrationRepository examRegistrationRepository;
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (userRepository.count() > 0) {
            return; // Data already initialized
        }

        // Create sample users
        User admin = new User();
        admin.setName("Admin User");
        admin.setEmail("admin@eduquest.com");
        admin.setPassword(bCryptPasswordEncoder.encode("admin123"));
        userRepository.save(admin);

        User student1 = new User();
        student1.setName("Akil Kumar");
        student1.setEmail("akil@student.com");
        student1.setPassword(bCryptPasswordEncoder.encode("student123"));
        userRepository.save(student1);

        User student2 = new User();
        student2.setName("Bharat Singh");
        student2.setEmail("bharat@student.com");
        student2.setPassword(bCryptPasswordEncoder.encode("student123"));
        userRepository.save(student2);

        User student3 = new User();
        student3.setName("Calypso Johnson");
        student3.setEmail("calypso@student.com");
        student3.setPassword(bCryptPasswordEncoder.encode("student123"));
        userRepository.save(student3);

        User student4 = new User();
        student4.setName("Kholi Patel");
        student4.setEmail("kholi@student.com");
        student4.setPassword(bCryptPasswordEncoder.encode("student123"));
        userRepository.save(student4);

        // Create sample exams
        Exam mathExam = new Exam();
        mathExam.setExamName("Math Exam");
        mathExam.setSubject("Mathematics");
        mathExam.setExamDate(LocalDateTime.of(2023, 10, 1, 10, 0));
        mathExam.setDuration("02:00:00");
        mathExam.setPrice(150.0);
        mathExam.setDescription("Basic mathematics exam");
        mathExam.setTotalQuestions(50);
        mathExam.setStatus("Upcoming");
        mathExam.setCreatedBy(admin);
        examRepository.save(mathExam);

        Exam mathFundamentals = new Exam();
        mathFundamentals.setExamName("Mathematics Fundamentals");
        mathFundamentals.setSubject("Math");
        mathFundamentals.setExamDate(LocalDateTime.now().plusDays(30));
        mathFundamentals.setDuration("01:30:00");
        mathFundamentals.setPrice(50.0);
        mathFundamentals.setDescription("Includes 1 questions");
        mathFundamentals.setTotalQuestions(1);
        mathFundamentals.setStatus("Upcoming");
        mathFundamentals.setCreatedBy(admin);
        examRepository.save(mathFundamentals);

        Exam physicsBasics = new Exam();
        physicsBasics.setExamName("Physics Basics");
        physicsBasics.setSubject("Physics");
        physicsBasics.setExamDate(LocalDateTime.now().plusDays(45));
        physicsBasics.setDuration("02:00:00");
        physicsBasics.setPrice(60.0);
        physicsBasics.setDescription("Includes 1 questions");
        physicsBasics.setTotalQuestions(1);
        physicsBasics.setStatus("Upcoming");
        physicsBasics.setCreatedBy(admin);
        examRepository.save(physicsBasics);

        // Create exam registrations
        ExamRegistration reg1 = new ExamRegistration(student1, mathExam);
        reg1.setStatus("Registered");
        examRegistrationRepository.save(reg1);

        ExamRegistration reg2 = new ExamRegistration(student2, mathExam);
        reg2.setStatus("Registered");
        examRegistrationRepository.save(reg2);

        ExamRegistration reg3 = new ExamRegistration(student3, mathExam);
        reg3.setStatus("Registered");
        examRegistrationRepository.save(reg3);

        ExamRegistration reg4 = new ExamRegistration(student4, mathExam);
        reg4.setStatus("Registered");
        examRegistrationRepository.save(reg4);

        // Create some registrations with different months for stats
        ExamRegistration regFeb = new ExamRegistration(student1, mathFundamentals);
        regFeb.setRegistrationDate(LocalDateTime.of(2024, 2, 15, 10, 0));
        examRegistrationRepository.save(regFeb);

        ExamRegistration regMar = new ExamRegistration(student2, physicsBasics);
        regMar.setRegistrationDate(LocalDateTime.of(2024, 3, 20, 14, 30));
        examRegistrationRepository.save(regMar);

        // Create sample payments
        Payment payment1 = new Payment(student1, mathExam, 150.0);
        payment1.setStatus("Paid");
        payment1.setPaymentMethod("Credit Card");
        payment1.setTransactionId("TXN001");
        payment1.setPaymentDate(LocalDateTime.of(2024, 3, 1, 9, 30));
        paymentRepository.save(payment1);

        Payment payment2 = new Payment(student2, mathExam, 150.0);
        payment2.setStatus("Pending");
        payment2.setPaymentMethod("Bank Transfer");
        payment2.setTransactionId("TXN002");
        payment2.setPaymentDate(LocalDateTime.of(2024, 3, 2, 11, 15));
        paymentRepository.save(payment2);

        Payment payment3 = new Payment(student3, mathExam, 200.0);
        payment3.setStatus("Paid");
        payment3.setPaymentMethod("UPI");
        payment3.setTransactionId("TXN003");
        payment3.setPaymentDate(LocalDateTime.of(2024, 3, 3, 16, 45));
        paymentRepository.save(payment3);

        Payment payment4 = new Payment(student4, mathExam, 200.0);
        payment4.setStatus("Paid");
        payment4.setPaymentMethod("Credit Card");
        payment4.setTransactionId("TXN004");
        payment4.setPaymentDate(LocalDateTime.of(2024, 2, 28, 13, 20));
        paymentRepository.save(payment4);

        // Add more payments for better stats
        for (int i = 1; i <= 8; i++) {
            Payment extraPayment = new Payment();
            extraPayment.setUser(i % 2 == 0 ? student1 : student2);
            extraPayment.setExam(mathExam);
            extraPayment.setAmount(100.0 + (i * 10));
            extraPayment.setStatus(i % 3 == 0 ? "Pending" : "Paid");
            extraPayment.setPaymentMethod("Online");
            extraPayment.setTransactionId("TXN00" + (i + 4));
            extraPayment.setPaymentDate(LocalDateTime.of(2024, i % 6 + 1, 15, 10, 0));
            paymentRepository.save(extraPayment);
        }

        System.out.println("✅ Sample data initialized successfully!");
        System.out.println("📧 Test login credentials:");
        System.out.println("   Admin: admin@eduquest.com / admin123");
        System.out.println("   Student: akil@student.com / student123");
    }
}