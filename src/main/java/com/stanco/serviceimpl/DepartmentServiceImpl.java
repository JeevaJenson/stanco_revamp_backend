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


   
    @Override
    public DepartmentResponse create(
            DepartmentRequest request,
            String createdBy) {

        String depId = request.getDepId().trim();
        String name = request.getName().trim();


        if (repository.existsByDepId(depId)) {

            throw new RuntimeException(
                    "Department ID already exists: " + depId
            );
        }


        Department department = new Department();

        department.setDepId(depId);

        department.setName(name);

        department.setStatus(
                request.getStatus() != null
                        ? request.getStatus()
                        : Status.active
        );

        department.setCreatedBy(createdBy);

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



    @Override
    public List<DepartmentResponse> getAll() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


  

    @Override
    public DepartmentResponse getById(
            Long id) {

        Department department =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Department not found: " + id
                                )
                        );


        return mapToResponse(department);
    }




    @Override
    public DepartmentResponse getByDepId(
            String depId) {

        if (depId == null ||
                depId.trim().isEmpty()) {

            throw new RuntimeException(
                    "Department ID is required"
            );
        }


        Department department =
                repository.findByDepId(
                        depId.trim()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Department not found: " + depId
                        )
                );


        return mapToResponse(department);
    }




    @Override
    public DepartmentResponse update(
            Long id,
            DepartmentRequest request,
            String updatedBy) {

        Department department =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Department not found: " + id
                                )
                        );


        String newDepId =
                request.getDepId().trim();

        String newName =
                request.getName().trim();


        if (!newDepId.equals(
                department.getDepId()
        )
                &&
                repository.existsByDepId(newDepId)) {

            throw new RuntimeException(
                    "Department ID already exists: "
                            + newDepId
            );
        }


        department.setDepId(newDepId);

        department.setName(newName);




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


        department.setUpdatedBy(updatedBy);

        department.setUpdatedAt(
                LocalDateTime.now()
        );


        Department updated =
                repository.save(department);


        return mapToResponse(updated);
    }


   

    @Override
    public void delete(
            Long id,
            String updatedBy) {

        Department department =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Department not found: " + id
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