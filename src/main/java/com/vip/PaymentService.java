package com.vip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private ExamRepository examRepository;
    
 // In PaymentService.java

    public Payment createPayment(User user, Long examId, Double amount, String paymentMethod) { // <-- ADD the String parameter
        Optional<Exam> optionalExam = examRepository.findById(examId);
        if (optionalExam.isPresent()) {
            Payment payment = new Payment(user, optionalExam.get(), amount);
            payment.setTransactionId(UUID.randomUUID().toString());
            payment.setPaymentMethod(paymentMethod); // <-- ADD this line to set the value
            return paymentRepository.save(payment);
        }
        return null;
    }
    
    public List<Payment> getUserPayments(User user) {
        return paymentRepository.findByUser(user);
    }
    
    public Payment updatePaymentStatus(Long paymentId, String status) {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            payment.setStatus(status);
            if ("Paid".equals(status)) {
                payment.setPaymentDate(LocalDateTime.now());
            }
            return paymentRepository.save(payment);
        }
        return null;
    }
    
    public Double getTotalRevenue() {
        Double revenue = paymentRepository.getTotalRevenue();
        return revenue != null ? revenue : 0.0;
    }
    
    public Long getPaidInvoicesCount() {
        return paymentRepository.countPaidInvoices();
    }
    
    public Long getPendingInvoicesCount() {
        return paymentRepository.countPendingInvoices();
    }
    
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}