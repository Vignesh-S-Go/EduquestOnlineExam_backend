package com.vip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUser(User user);
    List<Payment> findByStatus(String status);
    List<Payment> findByUserAndStatus(User user, String status);
    
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = 'Paid'")
    Double getTotalRevenue();
    
    @Query("SELECT COUNT(p) FROM Payment p WHERE p.status = 'Paid'")
    Long countPaidInvoices();
    
    @Query("SELECT COUNT(p) FROM Payment p WHERE p.status = 'Pending'")
    Long countPendingInvoices();
    
    @Query("SELECT p FROM Payment p WHERE p.paymentDate BETWEEN :startDate AND :endDate")
    List<Payment> findPaymentsByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT MONTH(p.paymentDate), SUM(p.amount) FROM Payment p WHERE p.status = 'Paid' AND YEAR(p.paymentDate) = :year GROUP BY MONTH(p.paymentDate) ORDER BY MONTH(p.paymentDate)")
    List<Object[]> getMonthlyRevenueStats(@Param("year") int year);
}