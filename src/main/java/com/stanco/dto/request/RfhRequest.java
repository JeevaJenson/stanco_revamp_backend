package com.stanco.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RfhRequest {

    private String ticketNumber;

    private String rollsOption;

    private String name;

    private String mobile;

    private String email;

    private String positionReports;

    private String reportEmail;

    // =====================================================
    // REQUEST RAISED BY
    // =====================================================

    private String costCenter;

    private String approvedBy;

    // =====================================================
    // BASIC DETAILS
    // =====================================================

    @NotBlank(message = "Request Type is required")
    private String requestType;

    private String replacementOf;

    private String approvalHire;

    private String requestDate;

    @NotBlank(message = "Client Name is required")
    private String clientName;

    // =====================================================
    // POSITION DETAILS
    // =====================================================

    private String positionTitle;

    private String location;

    private String locationPreferred;

    private String business;

    private String vertical;

    private String division;

    private String function;

    private String noOfPositions;

    // =====================================================
    // JOB REQUIREMENTS
    // =====================================================

    private String jdRoles;

    private String qualification;

    private String essentialSkill;

    private String goodSkill;

    // =====================================================
    // COMPENSATION
    // =====================================================

    private String experience;

    private String salaryRange;

    private String salaryRangeAnnual;

    // =====================================================
    // CATEGORY
    // =====================================================

    private String empCategory;

    private String type;

    // =====================================================
    // ADDITIONAL
    // =====================================================

    private String anySpecific;

    // =====================================================
    // OTHER BACKEND FIELDS
    // =====================================================

    private String deleteRemark;

    private Integer approvalHirePath;

    private String approveDate;

    private String department;

    private String designation;

    private String tenDoj;

    private String attendanceFormat;

    private String weekOff;

    private String ckSupervisior;

    private String ckMail;

    private String approverId;

    private String reporterId;
}