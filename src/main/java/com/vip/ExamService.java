package com.vip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExamService {
    
    @Autowired
    private ExamRepository examRepository;
    
    @Autowired
    private ExamRegistrationRepository examRegistrationRepository;
    
    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }
    
    public List<Exam> getAvailableExams() {
        return examRepository.findByStatus("Upcoming");
    }
    
    public Optional<Exam> getExamById(Long id) {
        return examRepository.findById(id);
    }
    
    public Exam createExam(Exam exam) {
        exam.setCreatedAt(LocalDateTime.now());
        exam.setUpdatedAt(LocalDateTime.now());
        return examRepository.save(exam);
    }
    
    public Exam updateExam(Long id, Exam examDetails) {
        Optional<Exam> optionalExam = examRepository.findById(id);
        if (optionalExam.isPresent()) {
            Exam exam = optionalExam.get();
            exam.setExamName(examDetails.getExamName());
            exam.setSubject(examDetails.getSubject());
            exam.setExamDate(examDetails.getExamDate());
            exam.setDuration(examDetails.getDuration());
            exam.setPrice(examDetails.getPrice());
            exam.setDescription(examDetails.getDescription());
            exam.setTotalQuestions(examDetails.getTotalQuestions());
            exam.setUpdatedAt(LocalDateTime.now());
            return examRepository.save(exam);
        }
        return null;
    }
    
    public boolean deleteExam(Long id) {
        if (examRepository.existsById(id)) {
            examRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public Long getTotalExamsCount() {
        return examRepository.countTotalExams();
    }
    
    public List<ExamRegistration> getUserRegisteredExams(User user) {
        return examRegistrationRepository.findByUser(user);
    }
}