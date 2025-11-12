package com.arqui.paymentservice.repository;

import com.arqui.paymentservice.entity.PaymentBill;
import com.arqui.paymentservice.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentBillRepository extends JpaRepository<PaymentBill, Long> {
    @Query("SELECT COALESCE(SUM(pb.total), 0) " +
            "FROM PaymentBill pb " +
            "WHERE FUNCTION('YEAR', pb.issueDate) = :year " +
            "AND FUNCTION('MONTH', pb.issueDate) BETWEEN :startMonth AND :endMonth")
    Double getTotalEarningsByPeriod(@Param("year") Integer year,
                                    @Param("startMonth") Integer startMonth,
                                    @Param("endMonth") Integer endMonth);
}
