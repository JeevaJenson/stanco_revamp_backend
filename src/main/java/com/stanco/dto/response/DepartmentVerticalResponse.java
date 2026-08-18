package com.stanco.dto.response;

import com.stanco.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartmentVerticalResponse {

    private Long id;

    private Long verticalId;

    private String verticalName;

    private Status status;
}