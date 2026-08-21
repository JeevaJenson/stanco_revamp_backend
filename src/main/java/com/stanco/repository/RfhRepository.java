package com.stanco.repository;

import com.stanco.entity.Rfh;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RfhRepository extends JpaRepository<Rfh, Long> {

        // =====================================================
        // FIND BY RES ID
        // =====================================================

        Optional<Rfh> findByResId(String resId);

        // =====================================================
        // FIND BY RFH NUMBER
        // ticketNumber = RFH Number
        // =====================================================

        Optional<Rfh> findByTicketNumber(String ticketNumber);

        // =====================================================
        // GET ACTIVE / DELETED RFH
        // =====================================================

        List<Rfh> findByDeleteStatus(Integer deleteStatus);

        // =====================================================
        // GET RFH CREATED BY EMPLOYEE
        // =====================================================

        List<Rfh> findByRequestBy(String requestBy);

        // =====================================================
        // CHECK RES ID EXISTS
        // =====================================================

        boolean existsByResId(String resId);

        // =====================================================
        // CHECK RFH NUMBER EXISTS
        // =====================================================

        boolean existsByTicketNumber(String ticketNumber);
}