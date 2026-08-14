package com.stanco.serviceimpl;

import com.stanco.dto.request.DepartmentRequest;
import com.stanco.dto.response.DepartmentResponse;

import com.stanco.entity.Department;

import com.stanco.enums.Status;

import com.stanco.repository.DepartmentRepository;

import com.stanco.service.DepartmentService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl
        implements DepartmentService {


    private final DepartmentRepository repository;


    // ==========================================
    // CREATE
    // ==========================================

    @Override
    public DepartmentResponse create(

            DepartmentRequest request,

            String createdBy) {


        if (repository.existsByDepId(
                request.getDepId())) {

            throw new RuntimeException(
                    "Department ID already exists: "
                            + request.getDepId()
            );
        }


        Department department =
                new Department();


        department.setDepId(
                request.getDepId()
        );


        department.setName(
                request.getName()
        );


        department.setStatus(
                request.getStatus() != null
                        ? request.getStatus()
                        : Status.active
        );


        // JWT logged-in user
        department.setCreatedBy(
                createdBy
        );


        department.setCreatedAt(
                LocalDateTime.now()
        );


        department.setUpdatedAt(
                LocalDateTime.now()
        );


        Department saved =
                repository.save(department);


        return mapToResponse(saved);
    }


    // ==========================================
    // GET ALL
    // ==========================================

    @Override
    public List<DepartmentResponse> getAll() {

        return repository
                .findByStatus(Status.active)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // ==========================================
    // GET BY ID
    // ==========================================

    @Override
    public DepartmentResponse getById(
            Long id) {

        Department department =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Department not found: "
                                                + id
                                )
                        );


        return mapToResponse(
                department
        );
    }


    // ==========================================
    // GET BY DEPARTMENT ID
    // ==========================================

    @Override
    public DepartmentResponse getByDepId(
            String depId) {

        Department department =
                repository.findByDepId(depId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Department not found: "
                                                + depId
                                )
                        );


        return mapToResponse(
                department
        );
    }


    // ==========================================
    // UPDATE
    // ==========================================

    @Override
    public DepartmentResponse update(

            Long id,

            DepartmentRequest request,

            String updatedBy) {


        Department department =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Department not found: "
                                                + id
                                )
                        );


        department.setDepId(
                request.getDepId()
        );


        department.setName(
                request.getName()
        );


        if (request.getStatus() != null) {

            department.setStatus(
                    request.getStatus()
            );


            if (request.getStatus()
                    == Status.active) {

                department.setDeletedAt(null);
            }


            if (request.getStatus()
                    == Status.inactive) {

                department.setDeletedAt(
                        LocalDateTime.now()
                );
            }
        }


        // JWT logged-in user
        department.setUpdatedBy(
                updatedBy
        );


        department.setUpdatedAt(
                LocalDateTime.now()
        );


        Department updated =
                repository.save(department);


        return mapToResponse(
                updated
        );
    }


    // ==========================================
    // DELETE - SOFT DELETE
    // ==========================================

    @Override
    public void delete(

            Long id,

            String updatedBy) {


        Department department =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Department not found: "
                                                + id
                                )
                        );


        department.setStatus(
                Status.inactive
        );


        department.setDeletedAt(
                LocalDateTime.now()
        );


        department.setUpdatedBy(
                updatedBy
        );


        department.setUpdatedAt(
                LocalDateTime.now()
        );


        repository.save(department);
    }


    // ==========================================
    // MAPPER
    // ==========================================

    private DepartmentResponse mapToResponse(

            Department department) {

        return new DepartmentResponse(

                department.getId(),

                department.getDepId(),

                department.getName(),

                department.getStatus(),

                department.getCreatedBy(),

                department.getUpdatedBy(),

                department.getCreatedAt(),

                department.getUpdatedAt(),

                department.getDeletedAt()
        );
    }
}