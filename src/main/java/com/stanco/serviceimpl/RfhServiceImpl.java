package com.stanco.serviceimpl;

import com.stanco.dto.request.RfhRequest;
import com.stanco.dto.response.RfhResponse;

import com.stanco.entity.Rfh;
import com.stanco.entity.RecruitmentRequest;

import com.stanco.repository.RfhRepository;
import com.stanco.repository.RecruitmentRequestRepository;

import com.stanco.service.RfhService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RfhServiceImpl implements RfhService {

        private final RfhRepository repository;

        private final RecruitmentRequestRepository recruitmentRequestRepository;

        // =========================================================
        // CREATE RFH
        // =========================================================

        @Override
        public RfhResponse create(
                        RfhRequest request,
                        String empID) {

                // -----------------------------------------------------
                // CREATE RFH
                // -----------------------------------------------------

                Rfh rfh = new Rfh();

                rfh.setResId(request.getResId());

                rfh.setTicketNumber(
                                request.getTicketNumber());

                rfh.setRollsOption(
                                request.getRollsOption());

                rfh.setName(
                                request.getName());

                rfh.setMobile(
                                request.getMobile());

                rfh.setEmail(
                                request.getEmail());

                rfh.setPositionReports(
                                request.getPositionReports());

                rfh.setReportEmail(
                                request.getReportEmail());

                rfh.setCostCenter(
                                request.getCostCenter());

                rfh.setApprovedBy(
                                request.getApprovedBy());

                rfh.setRequestType(
                                request.getRequestType());

                rfh.setReplacementOf(
                                request.getReplacementOf());

                rfh.setApprovalHire(
                                request.getApprovalHire());

                rfh.setPositionTitle(
                                request.getPositionTitle());

                rfh.setLocation(
                                request.getLocation());

                rfh.setLocationPreferred(
                                request.getLocationPreferred());

                rfh.setBusiness(
                                request.getBusiness());

                rfh.setBand(
                                request.getBand());

                rfh.setDivision(
                                request.getDivision());

                rfh.setFunction(
                                request.getFunction());

                rfh.setNoOfPositions(
                                request.getNoOfPositions());

                rfh.setJdRoles(
                                request.getJdRoles());

                rfh.setQualification(
                                request.getQualification());

                rfh.setEssentialSkill(
                                request.getEssentialSkill());

                rfh.setGoodSkill(
                                request.getGoodSkill());

                rfh.setExperience(
                                request.getExperience());

                rfh.setSalaryRange(
                                request.getSalaryRange());

                rfh.setSalaryRangeAnnual(
                                request.getSalaryRangeAnnual());

                rfh.setAnySpecific(
                                request.getAnySpecific());

                rfh.setCreatedDate(
                                LocalDateTime.now());

                rfh.setDeleteStatus(0);

                rfh.setDeleteRemark(
                                request.getDeleteRemark());

                rfh.setApprovalHirePath(
                                request.getApprovalHirePath());

                rfh.setRequestDate(
                                request.getRequestDate());

                // Logged-in employee
                rfh.setRequestBy(empID);

                rfh.setApproveDate(
                                request.getApproveDate());

                rfh.setDepartment(
                                request.getDepartment());

                rfh.setDesignation(
                                request.getDesignation());

                rfh.setVertical(
                                request.getVertical());

                rfh.setTenDoj(
                                request.getTenDoj());

                rfh.setEmpCategory(
                                request.getEmpCategory());

                rfh.setType(
                                request.getType());

                rfh.setAttendanceFormat(
                                request.getAttendanceFormat());

                rfh.setWeekOff(
                                request.getWeekOff());

                rfh.setCkSupervisior(
                                request.getCkSupervisior());

                rfh.setCkMail(
                                request.getCkMail());

                rfh.setApproverId(
                                request.getApproverId());

                rfh.setReporterId(
                                request.getReporterId());

                rfh.setClientName(
                                request.getClientName());

                // -----------------------------------------------------
                // SAVE RFH
                // -----------------------------------------------------

                Rfh savedRfh = repository.save(rfh);

                // -----------------------------------------------------
                // CREATE RECRUITMENT REQUEST
                // -----------------------------------------------------

                createRecruitmentRequest(
                                savedRfh,
                                empID);

                // -----------------------------------------------------
                // RETURN RFH RESPONSE
                // -----------------------------------------------------

                return mapToResponse(savedRfh);
        }

        // =========================================================
        // CREATE RECRUITMENT REQUEST FROM RFH
        // =========================================================

        private void createRecruitmentRequest(
                        Rfh rfh,
                        String empID) {

                // -----------------------------------------------------
                // Generate Rec Req ID
                // -----------------------------------------------------

                String recReqID = generateRecReqID();

                // -----------------------------------------------------
                // Create entity
                // -----------------------------------------------------

                RecruitmentRequest recruitmentRequest = new RecruitmentRequest();

                recruitmentRequest.setRecReqID(
                                recReqID);

                // -----------------------------------------------------
                // RFH DATA
                // -----------------------------------------------------

                recruitmentRequest.setRfhNo(
                                rfh.getTicketNumber());

                recruitmentRequest.setPositionTitle(
                                rfh.getPositionTitle());

                recruitmentRequest.setNoOfPosition(
                                rfh.getNoOfPositions());

                recruitmentRequest.setBand(
                                rfh.getBand());

                recruitmentRequest.setBusiness(
                                rfh.getBusiness());

                recruitmentRequest.setDivision(
                                rfh.getDivision());

                recruitmentRequest.setFunction(
                                rfh.getFunction());

                recruitmentRequest.setLocation(
                                rfh.getLocation());

                recruitmentRequest.setSalaryRange(
                                rfh.getSalaryRange());

                recruitmentRequest.setSalaryRangeAnnual(
                                rfh.getSalaryRangeAnnual());

                // -----------------------------------------------------
                // REQUEST STATUS
                // -----------------------------------------------------

                recruitmentRequest.setRequestStatus(
                                "Open");

                // -----------------------------------------------------
                // ALLOCATION STATUS
                // -----------------------------------------------------

                recruitmentRequest.setAssignedStatus(
                                "Unassigned");

                recruitmentRequest.setAssignedTo(
                                null);

                recruitmentRequest.setAssignedDate(
                                null);

                // -----------------------------------------------------
                // OTHER DATA
                // -----------------------------------------------------

                recruitmentRequest.setOpenDate(
                                java.time.LocalDate.now());

                recruitmentRequest.setCreatedBy(
                                empID);

                recruitmentRequest.setModifiedBy(
                                null);

                recruitmentRequest.setCreatedAt(
                                LocalDateTime.now());

                recruitmentRequest.setUpdatedAt(
                                LocalDateTime.now());

                recruitmentRequest.setDeleteStatus(
                                0);

                recruitmentRequest.setSubPositionTitle(
                                null);

                recruitmentRequest.setClosedBy(
                                null);

                // -----------------------------------------------------
                // SAVE
                // -----------------------------------------------------

                recruitmentRequestRepository.save(
                                recruitmentRequest);
        }

        // =========================================================
        // GENERATE REC REQ ID
        // =========================================================

        private String generateRecReqID() {

                long count = recruitmentRequestRepository.count();

                return "REC" +
                                String.format(
                                                "%03d",
                                                count + 1);
        }

        // =========================================================
        // GET ALL
        // =========================================================

        @Override
        public List<RfhResponse> getAll() {

                return repository.findAll()
                                .stream()
                                .map(this::mapToResponse)
                                .toList();
        }

        // =========================================================
        // GET BY ID
        // =========================================================

        @Override
        public RfhResponse getById(
                        Long id) {

                Rfh rfh = repository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "RFH not found: " + id));

                return mapToResponse(rfh);
        }

        // =========================================================
        // GET BY RES ID
        // =========================================================

        @Override
        public RfhResponse getByResId(
                        String resId) {

                Rfh rfh = repository.findByResId(resId)
                                .orElseThrow(() -> new RuntimeException(
                                                "RFH not found: " + resId));

                return mapToResponse(rfh);
        }

        // =========================================================
        // GET MY RFH
        // =========================================================

        @Override
        public List<RfhResponse> getMyRfh(
                        String empID) {

                return repository
                                .findByRequestBy(empID)
                                .stream()
                                .map(this::mapToResponse)
                                .toList();
        }

        // =========================================================
        // UPDATE
        // =========================================================

        @Override
        public RfhResponse update(
                        Long id,
                        RfhRequest request) {

                Rfh rfh = repository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "RFH not found: " + id));

                rfh.setPositionTitle(
                                request.getPositionTitle());

                rfh.setLocation(
                                request.getLocation());

                rfh.setBusiness(
                                request.getBusiness());

                rfh.setBand(
                                request.getBand());

                rfh.setDivision(
                                request.getDivision());

                rfh.setFunction(
                                request.getFunction());

                rfh.setNoOfPositions(
                                request.getNoOfPositions());

                rfh.setSalaryRange(
                                request.getSalaryRange());

                rfh.setSalaryRangeAnnual(
                                request.getSalaryRangeAnnual());

                rfh.setApproveDate(
                                request.getApproveDate());

                Rfh updated = repository.save(rfh);

                return mapToResponse(updated);
        }

        // =========================================================
        // DELETE
        // =========================================================

        @Override
        public void delete(
                        Long id,
                        String remark) {

                Rfh rfh = repository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "RFH not found: " + id));

                rfh.setDeleteStatus(1);

                rfh.setDeleteRemark(
                                remark);

                repository.save(rfh);
        }

        // =========================================================
        // MAP RESPONSE
        // =========================================================

        private RfhResponse mapToResponse(
                        Rfh rfh) {

                return new RfhResponse(

                                rfh.getId(),

                                rfh.getResId(),

                                rfh.getRollsOption(),

                                rfh.getName(),

                                rfh.getMobile(),

                                rfh.getEmail(),

                                rfh.getPositionReports(),

                                rfh.getReportEmail(),

                                rfh.getCostCenter(),

                                rfh.getApprovedBy(),

                                rfh.getRequestType(),

                                rfh.getReplacementOf(),

                                rfh.getApprovalHire(),

                                rfh.getTicketNumber(),

                                rfh.getPositionTitle(),

                                rfh.getLocation(),

                                rfh.getLocationPreferred(),

                                rfh.getBusiness(),

                                rfh.getBand(),

                                rfh.getDivision(),

                                rfh.getFunction(),

                                rfh.getNoOfPositions(),

                                rfh.getJdRoles(),

                                rfh.getQualification(),

                                rfh.getEssentialSkill(),

                                rfh.getGoodSkill(),

                                rfh.getExperience(),

                                rfh.getSalaryRange(),

                                rfh.getSalaryRangeAnnual(),

                                rfh.getAnySpecific(),

                                rfh.getCreatedDate(),

                                rfh.getDeleteStatus(),

                                rfh.getDeleteRemark(),

                                rfh.getApprovalHirePath(),

                                rfh.getRequestDate(),

                                rfh.getRequestBy(),

                                rfh.getApproveDate(),

                                rfh.getDepartment(),

                                rfh.getDesignation(),

                                rfh.getVertical(),

                                rfh.getTenDoj(),

                                rfh.getEmpCategory(),

                                rfh.getType(),

                                rfh.getAttendanceFormat(),

                                rfh.getWeekOff(),

                                rfh.getCkSupervisior(),

                                rfh.getCkMail(),

                                rfh.getApproverId(),

                                rfh.getReporterId(),

                                rfh.getClientName());
        }
}