package com.vip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByStatus(String status);
    List<Exam> findBySubject(String subject);
    List<Exam> findByCreatedBy(User user);
    
    @Query("SELECT COUNT(e) FROM Exam e")
    Long countTotalExams();
    
    @Query("SELECT COUNT(e) FROM Exam e WHERE e.examDate BETWEEN :startDate AND :endDate")
    Long countExamsByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT e.subject, COUNT(e) FROM Exam e GROUP BY e.subject")
    List<Object[]> countExamsBySubject();
}