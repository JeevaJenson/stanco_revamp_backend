package com.stanco.repository;

import com.stanco.entity.Department;
import com.stanco.enums.Status;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository
        extends JpaRepository<Department, Long> {

    boolean existsByDepId(
            String depId
    );

    Optional<Department> findByDepId(
            String depId
    );

    List<Department> findByStatus(
            Status status
    );


    long countByVertical_IdAndStatus(
            Long verticalId,
            Status status
    );
}