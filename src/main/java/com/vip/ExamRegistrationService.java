package com.vip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExamRegistrationService {
    
    @Autowired
    private ExamRegistrationRepository examRegistrationRepository;
    
    @Autowired
    private ExamRepository examRepository;
    
    public ExamRegistration registerUserForExam(User user, Long examId) {
        Optional<Exam> optionalExam = examRepository.findById(examId);
        if (optionalExam.isPresent()) {
            ExamRegistration registration = new ExamRegistration(user, optionalExam.get());
            return examRegistrationRepository.save(registration);
        }
        return null;
    }
    
    public List<ExamRegistration> getUserRegistrations(User user) {
        return examRegistrationRepository.findByUser(user);
    }
    
    public Long getTotalStudentsCount() {
        return examRegistrationRepository.countUniqueStudents();
    }
    
    public List<Object[]> getMonthlyRegistrationStats(int year) {
        return examRegistrationRepository.getMonthlyRegistrationStats(year);
    }
    
    public ExamRegistration startExam(Long registrationId) {
        Optional<ExamRegistration> optionalRegistration = examRegistrationRepository.findById(registrationId);
        if (optionalRegistration.isPresent()) {
            ExamRegistration registration = optionalRegistration.get();
            registration.setStatus("In Progress");
            return examRegistrationRepository.save(registration);
        }
        return null;
    }
    
    public ExamRegistration completeExam(Long registrationId, Double score) {
        Optional<ExamRegistration> optionalRegistration = examRegistrationRepository.findById(registrationId);
        if (optionalRegistration.isPresent()) {
            ExamRegistration registration = optionalRegistration.get();
            registration.setStatus("Completed");
            registration.setScore(score);
            registration.setCompletionDate(LocalDateTime.now());
            return examRegistrationRepository.save(registration);
        }
        return null;
    }
}