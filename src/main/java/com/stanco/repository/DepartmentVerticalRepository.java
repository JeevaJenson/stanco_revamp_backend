package com.stanco.repository;

import com.stanco.entity.DepartmentVertical;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentVerticalRepository
        extends JpaRepository<DepartmentVertical, Long> {

    List<DepartmentVertical> findByDepartment_Id(
            Long departmentId);

    long countByDepartment_Id(
            Long departmentId);

    boolean existsByDepartment_IdAndVertical_Id(
            Long departmentId,
            Long verticalId);

    void deleteByDepartment_IdAndVertical_Id(
            Long departmentId,
            Long verticalId);
}