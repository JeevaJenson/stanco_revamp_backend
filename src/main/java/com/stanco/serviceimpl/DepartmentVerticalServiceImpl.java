package com.stanco.serviceimpl;

import com.stanco.dto.response.DepartmentVerticalResponse;

import com.stanco.entity.Department;
import com.stanco.entity.DepartmentVertical;
import com.stanco.entity.Vertical;

import com.stanco.repository.DepartmentRepository;
import com.stanco.repository.DepartmentVerticalRepository;
import com.stanco.repository.VerticalRepository;

import com.stanco.service.DepartmentVerticalService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentVerticalServiceImpl
        implements DepartmentVerticalService {

    private final DepartmentRepository departmentRepository;

    private final VerticalRepository verticalRepository;

    private final DepartmentVerticalRepository mappingRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentVerticalResponse> getVerticalsByDepartment(
            Long departmentId) {

        departmentRepository
                .findById(
                        departmentId)
                .orElseThrow(() -> new RuntimeException(
                        "Department not found: "
                                + departmentId));

        List<DepartmentVertical> mappings = mappingRepository
                .findByDepartment_Id(
                        departmentId);

        return mappings
                .stream()
                .map(mapping -> {

                    Vertical vertical = mapping.getVertical();

                    return new DepartmentVerticalResponse(

                            mapping.getId(),

                            vertical.getId(),

                            vertical.getVerticalName(),

                            vertical.getStatus()

                );

                })
                .toList();
    }

    @Override
    public DepartmentVerticalResponse addVerticalToDepartment(

            Long departmentId,

            Long verticalId

    ) {

        Department department = departmentRepository
                .findById(
                        departmentId)
                .orElseThrow(() -> new RuntimeException(
                        "Department not found: "
                                + departmentId));

        Vertical vertical = verticalRepository
                .findById(
                        verticalId)
                .orElseThrow(() -> new RuntimeException(
                        "Vertical not found: "
                                + verticalId));

        if (mappingRepository
                .existsByDepartment_IdAndVertical_Id(
                        departmentId,
                        verticalId)) {

            throw new RuntimeException(
                    "Vertical already mapped to this department");
        }

        DepartmentVertical mapping = new DepartmentVertical();

        mapping.setDepartment(
                department);

        mapping.setVertical(
                vertical);

        DepartmentVertical saved = mappingRepository.save(
                mapping);

        return new DepartmentVerticalResponse(

                saved.getId(),

                vertical.getId(),

                vertical.getVerticalName(),

                vertical.getStatus()

        );
    }

    @Override
    public void removeVerticalFromDepartment(

            Long departmentId,

            Long verticalId

    ) {

        if (!mappingRepository
                .existsByDepartment_IdAndVertical_Id(
                        departmentId,
                        verticalId)) {

            throw new RuntimeException(
                    "Vertical mapping not found");
        }

        mappingRepository
                .deleteByDepartment_IdAndVertical_Id(
                        departmentId,
                        verticalId);
    }
}