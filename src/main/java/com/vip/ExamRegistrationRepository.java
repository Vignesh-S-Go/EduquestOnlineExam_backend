package com.vip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface ExamRegistrationRepository extends JpaRepository<ExamRegistration, Long> {
    List<ExamRegistration> findByUser(User user);
    List<ExamRegistration> findByExam(Exam exam);
    List<ExamRegistration> findByUserAndStatus(User user, String status);
    
    @Query("SELECT COUNT(er) FROM ExamRegistration er")
    Long countTotalRegistrations();
    
    @Query("SELECT COUNT(DISTINCT er.user) FROM ExamRegistration er")
    Long countUniqueStudents();
    
    @Query("SELECT COUNT(er) FROM ExamRegistration er WHERE er.registrationDate BETWEEN :startDate AND :endDate")
    Long countRegistrationsByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT MONTH(er.registrationDate), COUNT(er) FROM ExamRegistration er WHERE YEAR(er.registrationDate) = :year GROUP BY MONTH(er.registrationDate) ORDER BY MONTH(er.registrationDate)")
    List<Object[]> getMonthlyRegistrationStats(@Param("year") int year);
}