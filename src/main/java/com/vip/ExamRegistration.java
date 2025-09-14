package com.vip;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "exam_registrations")
public class ExamRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;
    
    private LocalDateTime registrationDate = LocalDateTime.now();
    private String status = "Registered"; // "Registered", "Completed", "In Progress"
    private Double score;
    private LocalDateTime completionDate;
    
    // Constructors
    public ExamRegistration() {}
    
    public ExamRegistration(User user, Exam exam) {
        this.user = user;
        this.exam = exam;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public Exam getExam() { return exam; }
    public void setExam(Exam exam) { this.exam = exam; }
    
    public LocalDateTime getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDateTime registrationDate) { this.registrationDate = registrationDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
    
    public LocalDateTime getCompletionDate() { return completionDate; }
    public void setCompletionDate(LocalDateTime completionDate) { this.completionDate = completionDate; }
}
