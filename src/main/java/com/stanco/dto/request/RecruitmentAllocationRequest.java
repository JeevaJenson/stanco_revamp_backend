package com.stanco.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RecruitmentAllocationRequest {

    @NotBlank(message = "Recruiter ID is required")
    private String assignedTo;

    @NotBlank(message = "Allocation date is required")
    private String assignedDate;
}