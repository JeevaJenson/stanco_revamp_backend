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

    private String costCenter;
    private String approvedBy;

    @NotBlank(message = "Request Type is required")
    private String requestType;

    private String replacementOf;
    private String approvalHire;
    private String requestDate;

    @NotBlank(message = "Client Name is required")
    private String clientName;

    private String positionTitle;
    private String location;
    private String locationPreferred;
    private String business;
    private String vertical;
    private String division;
    private String function;
    private String noOfPositions;

    private String jdRoles;
    private String qualification;
    private String essentialSkill;
    private String goodSkill;

    private String experience;
    private String salaryRange;
    private String salaryRangeAnnual;

    private String empCategory;
    private String type;

    private String anySpecific;

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