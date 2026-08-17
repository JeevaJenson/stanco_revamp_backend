package com.stanco.service;

import com.stanco.dto.request.DepartmentRequest;
import com.stanco.dto.response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {

    DepartmentResponse create(
            DepartmentRequest request,
            String createdBy
    );

    List<DepartmentResponse> getAll();

    DepartmentResponse getById(
            Long id
    );

    DepartmentResponse getByDepId(
            String depId
    );

    DepartmentResponse update(
            Long id,
            DepartmentRequest request,
            String updatedBy
    );

    void delete(
            Long id,
            String updatedBy
    );
}