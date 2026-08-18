package com.stanco.serviceimpl;

import com.stanco.dto.request.DepartmentRequest;
import com.stanco.dto.response.DepartmentResponse;

import com.stanco.entity.Department;
import com.stanco.entity.DepartmentVertical;
import com.stanco.entity.Vertical;

import com.stanco.enums.Status;

import com.stanco.repository.DepartmentRepository;
import com.stanco.repository.DepartmentVerticalRepository;
import com.stanco.repository.VerticalRepository;

import com.stanco.service.DepartmentService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentServiceImpl
        implements DepartmentService {


    private final DepartmentRepository repository;

    private final VerticalRepository verticalRepository;

    private final DepartmentVerticalRepository
            departmentVerticalRepository;


    

    @Override
    public DepartmentResponse create(
            DepartmentRequest request,
            String createdBy
    ) {


        String depId =
                request.getDepId().trim();

        String name =
                request.getName().trim();


   

        if (repository.existsByDepId(depId)) {

            throw new RuntimeException(
                    "Department ID already exists: "
                            + depId
            );
        }


   

        Vertical vertical =
                verticalRepository
                        .findById(
                                request.getVerticalId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Vertical not found: "
                                                + request.getVerticalId()
                                )
                        );


   

        Department department =
                new Department();


        department.setDepId(
                depId
        );


        department.setName(
                name
        );


        department.setStatus(
                request.getStatus() != null
                        ? request.getStatus()
                        : Status.active
        );


        department.setCreatedBy(
                createdBy
        );


        LocalDateTime now =
                LocalDateTime.now();


        department.setCreatedAt(
                now
        );


        department.setUpdatedAt(
                now
        );


     

        Department saved =
                repository.save(
                        department
                );


     

        DepartmentVertical mapping =
                new DepartmentVertical();


        mapping.setDepartment(
                saved
        );


        mapping.setVertical(
                vertical
        );


        departmentVerticalRepository.save(
                mapping
        );


     

        return mapToResponse(
                saved
        );
    }


 

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponse> getAll() {


        return repository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }




    @Override
    @Transactional(readOnly = true)
    public DepartmentResponse getById(
            Long id
    ) {


        Department department =
                repository
                        .findById(id)
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


  

    @Override
    @Transactional(readOnly = true)
    public DepartmentResponse getByDepId(
            String depId
    ) {


        if (
                depId == null ||
                depId.trim().isEmpty()
        ) {

            throw new RuntimeException(
                    "Department ID is required"
            );
        }


        Department department =
                repository
                        .findByDepId(
                                depId.trim()
                        )
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


   

    @Override
    public DepartmentResponse update(

            Long id,

            DepartmentRequest request,

            String updatedBy

    ) {


       

        Department department =
                repository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Department not found: "
                                                + id
                                )
                        );


        String newDepId =
                request
                        .getDepId()
                        .trim();


        String newName =
                request
                        .getName()
                        .trim();


    

        if (
                !newDepId.equals(
                        department.getDepId()
                )
                &&
                repository.existsByDepId(
                        newDepId
                )
        ) {

            throw new RuntimeException(
                    "Department ID already exists: "
                            + newDepId
            );
        }


  

        Vertical vertical =
                verticalRepository
                        .findById(
                                request.getVerticalId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Vertical not found: "
                                                + request.getVerticalId()
                                )
                        );



        department.setDepId(
                newDepId
        );


        department.setName(
                newName
        );


    

        if (
                request.getStatus() != null
        ) {

            department.setStatus(
                    request.getStatus()
            );


            if (
                    request.getStatus()
                            == Status.active
            ) {

                department.setDeletedAt(
                        null
                );
            }


            if (
                    request.getStatus()
                            == Status.inactive
            ) {

                if (
                        department.getDeletedAt()
                                == null
                ) {

                    department.setDeletedAt(
                            LocalDateTime.now()
                    );
                }
            }
        }


        department.setUpdatedBy(
                updatedBy
        );


        department.setUpdatedAt(
                LocalDateTime.now()
        );


   

        Department updated =
                repository.save(
                        department
                );


      

        List<DepartmentVertical> mappings =
                departmentVerticalRepository
                        .findByDepartment_Id(
                                id
                        );


        // Remove old mappings

        if (!mappings.isEmpty()) {

            departmentVerticalRepository
                    .deleteAll(
                            mappings
                    );
        }


      

        DepartmentVertical newMapping =
                new DepartmentVertical();


        newMapping.setDepartment(
                updated
        );


        newMapping.setVertical(
                vertical
        );


        departmentVerticalRepository.save(
                newMapping
        );


      

        return mapToResponse(
                updated
        );
    }


   

    @Override
    public void delete(

            Long id,

            String updatedBy

    ) {


        Department department =
                repository
                        .findById(id)
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



        List<DepartmentVertical> mappings =
                departmentVerticalRepository
                        .findByDepartment_Id(
                                id
                        );


        if (!mappings.isEmpty()) {

            departmentVerticalRepository
                    .deleteAll(
                            mappings
                    );
        }


        repository.save(
                department
        );
    }


    // =====================================================
    // MAP RESPONSE
    // =====================================================

    private DepartmentResponse mapToResponse(

            Department department

    ) {


        Long verticalCount =
                departmentVerticalRepository
                        .countByDepartment_Id(
                                department.getId()
                        );


        return new DepartmentResponse(

                department.getId(),

                department.getDepId(),

                department.getName(),

                department.getStatus(),

                verticalCount,

                department.getCreatedBy(),

                department.getUpdatedBy(),

                department.getCreatedAt(),

                department.getUpdatedAt(),

                department.getDeletedAt()

        );
    }
}