package com.stanco.service;

import com.stanco.dto.response.DepartmentVerticalResponse;

import java.util.List;

public interface DepartmentVerticalService {

    List<DepartmentVerticalResponse>
    getVerticalsByDepartment(
            Long departmentId
    );


    DepartmentVerticalResponse
    addVerticalToDepartment(
            Long departmentId,
            Long verticalId
    );


    void removeVerticalFromDepartment(
            Long departmentId,
            Long verticalId
    );
}