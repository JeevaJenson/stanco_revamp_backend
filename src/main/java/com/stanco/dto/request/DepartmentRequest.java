package com.stanco.dto.request;

import com.stanco.enums.Status;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class DepartmentRequest {

    @NotBlank(message = "Department ID is required")
    private String depId;


    @NotBlank(message = "Department name is required")
    private String name;


    private Status status;
}