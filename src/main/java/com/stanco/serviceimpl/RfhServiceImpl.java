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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RfhServiceImpl implements RfhService {

        private final RfhRepository rfhRepository;

        private final RecruitmentRequestRepository recruitmentRequestRepository;

        // =========================================================
        // CREATE RFH
        // RFH + RECRUITMENT REQUEST
        // =========================================================

        @Override
        public RfhResponse create(
                        RfhRequest request,
                        String requestBy) {

                // =====================================================
                // 1. GENERATE RES ID
                // Example: RES0001
                // =====================================================

                String resId = generateResId();

                // =====================================================
                // 2. GENERATE RFH NUMBER
                // Example: RFH0001
                // =====================================================

                String rfhNumber = generateRfhNumber();

                // =====================================================
                // 3. CREATE RFH ENTITY
                // =====================================================

                Rfh rfh = new Rfh();

                // =====================================================
                // AUTO GENERATED VALUES
                // =====================================================

                rfh.setResId(resId);

                rfh.setTicketNumber(rfhNumber);

                // =====================================================
                // BASIC DETAILS
                // =====================================================

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

                // =====================================================
                // REQUEST RAISED BY
                // =====================================================

                rfh.setCostCenter(
                                request.getCostCenter());

                rfh.setApprovedBy(
                                request.getApprovedBy());

                // =====================================================
                // BASIC REQUEST DETAILS
                // =====================================================

                rfh.setRequestType(
                                request.getRequestType());

                rfh.setReplacementOf(
                                request.getReplacementOf());

                rfh.setApprovalHire(
                                request.getApprovalHire());

                rfh.setRequestDate(
                                request.getRequestDate());

                rfh.setClientName(
                                request.getClientName());

                // =====================================================
                // POSITION DETAILS
                // =====================================================

                rfh.setPositionTitle(
                                request.getPositionTitle());

                rfh.setLocation(
                                request.getLocation());

                rfh.setLocationPreferred(
                                request.getLocationPreferred());

                rfh.setBusiness(
                                request.getBusiness());

                rfh.setVertical(
                                request.getVertical());

                rfh.setDivision(
                                request.getDivision());

                rfh.setFunction(
                                request.getFunction());

                rfh.setNoOfPositions(
                                request.getNoOfPositions());

                // =====================================================
                // JOB REQUIREMENTS
                // =====================================================

                rfh.setJdRoles(
                                request.getJdRoles());

                rfh.setQualification(
                                request.getQualification());

                rfh.setEssentialSkill(
                                request.getEssentialSkill());

                rfh.setGoodSkill(
                                request.getGoodSkill());

                // =====================================================
                // COMPENSATION
                // =====================================================

                rfh.setExperience(
                                request.getExperience());

                rfh.setSalaryRange(
                                request.getSalaryRange());

                rfh.setSalaryRangeAnnual(
                                request.getSalaryRangeAnnual());

                // =====================================================
                // CATEGORY
                // =====================================================

                rfh.setEmpCategory(
                                request.getEmpCategory());

                rfh.setType(
                                request.getType());

                // =====================================================
                // ADDITIONAL
                // =====================================================

                rfh.setAnySpecific(
                                request.getAnySpecific());

                // =====================================================
                // SYSTEM FIELDS
                // =====================================================

                rfh.setCreatedDate(
                                LocalDateTime.now());

                rfh.setDeleteStatus(0);

                rfh.setDeleteRemark(
                                request.getDeleteRemark());

                rfh.setApprovalHirePath(
                                request.getApprovalHirePath() != null
                                                ? request.getApprovalHirePath()
                                                : 0);

                rfh.setRequestBy(
                                requestBy);

                rfh.setApproveDate(
                                request.getApproveDate());

                rfh.setDepartment(
                                request.getDepartment());

                rfh.setDesignation(
                                request.getDesignation());

                rfh.setTenDoj(
                                request.getTenDoj());

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

                // =====================================================
                // 4. SAVE RFH
                // =====================================================

                Rfh savedRfh = rfhRepository.save(rfh);

                // =====================================================
                // 5. CREATE RECRUITMENT REQUEST
                // =====================================================

                RecruitmentRequest recruitmentRequest = new RecruitmentRequest();

                // =====================================================
                // AUTO GENERATE REC REQUEST ID
                // Example: REC0001
                // =====================================================

                recruitmentRequest.setRecReqID(
                                generateRecruitmentRequestId());

                // =====================================================
                // LINK RFH NUMBER
                // recruitment_requests.rfh_no = RFH0001
                // =====================================================

                recruitmentRequest.setRfhNo(
                                savedRfh.getTicketNumber());

                // =====================================================
                // COPY RFH DATA
                // =====================================================

                recruitmentRequest.setPositionTitle(
                                request.getPositionTitle());

                recruitmentRequest.setNoOfPosition(
                                request.getNoOfPositions());

                recruitmentRequest.setBusiness(
                                request.getBusiness());

                recruitmentRequest.setDivision(
                                request.getDivision());

                recruitmentRequest.setFunction(
                                request.getFunction());

                recruitmentRequest.setLocation(
                                request.getLocation());

                recruitmentRequest.setSalaryRange(
                                request.getSalaryRange());

                recruitmentRequest.setSalaryRangeAnnual(
                                request.getSalaryRangeAnnual());

                // =====================================================
                // DATE
                // =====================================================

                recruitmentRequest.setOpenDate(
                                parseDate(request.getRequestDate()));

                // =====================================================
                // STATUS
                // =====================================================

                recruitmentRequest.setRequestStatus(
                                "OPEN");

                recruitmentRequest.setAssignedStatus(
                                "Unassigned");

                recruitmentRequest.setAssignedTo(
                                null);

                recruitmentRequest.setAssignedDate(
                                null);

                // =====================================================
                // OTHER RECRUITMENT FIELDS
                // =====================================================

                recruitmentRequest.setBand(null);

                recruitmentRequest.setCriticalPosition(null);

                recruitmentRequest.setBillingStatus(null);

                recruitmentRequest.setInterviewer(null);

                recruitmentRequest.setCloseDate(null);

                recruitmentRequest.setHeplRecruitmentRefNumber(null);

                recruitmentRequest.setActionForTheDayStatus(null);

                recruitmentRequest.setSubPositionTitle(null);

                recruitmentRequest.setClosedBy(null);

                // =====================================================
                // AUDIT
                // =====================================================

                recruitmentRequest.setCreatedBy(
                                requestBy);

                recruitmentRequest.setModifiedBy(
                                requestBy);

                recruitmentRequest.setCreatedAt(
                                LocalDateTime.now());

                recruitmentRequest.setUpdatedAt(
                                LocalDateTime.now());

                recruitmentRequest.setDeleteStatus(0);

                // =====================================================
                // 6. SAVE RECRUITMENT REQUEST
                // =====================================================

                recruitmentRequestRepository.save(
                                recruitmentRequest);

                // =====================================================
                // 7. RETURN RFH RESPONSE
                // =====================================================

                return mapToResponse(savedRfh);
        }

        // =========================================================
        // GENERATE RES ID
        // RES0001
        // RES0002
        // =========================================================

        private String generateResId() {

                long number = rfhRepository.count() + 1;

                String resId = "RES" + String.format(
                                "%04d",
                                number);

                while (rfhRepository.existsByResId(resId)) {

                        number++;

                        resId = "RES" + String.format(
                                        "%04d",
                                        number);
                }

                return resId;
        }

        // =========================================================
        // GENERATE RFH NUMBER
        // RFH0001
        // RFH0002
        // =========================================================

        private String generateRfhNumber() {

                long number = rfhRepository.count() + 1;

                String rfhNumber = "RFH" + String.format(
                                "%04d",
                                number);

                while (rfhRepository.existsByTicketNumber(
                                rfhNumber)) {

                        number++;

                        rfhNumber = "RFH" + String.format(
                                        "%04d",
                                        number);
                }

                return rfhNumber;
        }

        // =========================================================
        // GENERATE RECRUITMENT REQUEST ID
        // REC0001
        // REC0002
        // =========================================================

        private String generateRecruitmentRequestId() {

                long number = recruitmentRequestRepository.count() + 1;

                String recReqID = "REC" + String.format(
                                "%04d",
                                number);

                while (recruitmentRequestRepository
                                .existsByRecReqID(recReqID)) {

                        number++;

                        recReqID = "REC" + String.format(
                                        "%04d",
                                        number);
                }

                return recReqID;
        }

        // =========================================================
        // DATE CONVERSION
        // =========================================================

        private LocalDate parseDate(
                        String date) {

                if (date == null ||
                                date.trim().isEmpty()) {

                        return LocalDate.now();
                }

                try {

                        return LocalDate.parse(date);

                } catch (Exception e) {

                        return LocalDate.now();
                }
        }

        // =========================================================
        // GET ALL
        // =========================================================

        @Override
        @Transactional(readOnly = true)
        public List<RfhResponse> getAll() {

                return rfhRepository.findAll()
                                .stream()
                                .filter(rfh -> rfh.getDeleteStatus() == null ||
                                                rfh.getDeleteStatus() == 0)
                                .map(this::mapToResponse)
                                .toList();
        }

        // =========================================================
        // GET BY ID
        // =========================================================

        @Override
        @Transactional(readOnly = true)
        public RfhResponse getById(
                        Long id) {

                Rfh rfh = rfhRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "RFH not found: " + id));

                return mapToResponse(rfh);
        }

        // =========================================================
        // GET BY RES ID
        // =========================================================

        @Override
        @Transactional(readOnly = true)
        public RfhResponse getByResId(
                        String resId) {

                Rfh rfh = rfhRepository.findByResId(resId)
                                .orElseThrow(() -> new RuntimeException(
                                                "RFH not found with RES ID: "
                                                                + resId));

                return mapToResponse(rfh);
        }

        // =========================================================
        // GET MY RFH
        // =========================================================

        @Override
        @Transactional(readOnly = true)
        public List<RfhResponse> getMyRfh(
                        String requestBy) {

                return rfhRepository
                                .findByRequestBy(requestBy)
                                .stream()
                                .filter(rfh -> rfh.getDeleteStatus() == null ||
                                                rfh.getDeleteStatus() == 0)
                                .map(this::mapToResponse)
                                .toList();
        }

        // =========================================================
        // UPDATE RFH
        // =========================================================

        @Override
        public RfhResponse update(
                        Long id,
                        RfhRequest request) {

                Rfh rfh = rfhRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "RFH not found: " + id));

                // =====================================================
                // AUTO GENERATED VALUES
                // DO NOT CHANGE
                // =====================================================

                // resId remains same
                // ticketNumber remains same

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

                rfh.setRequestDate(
                                request.getRequestDate());

                rfh.setClientName(
                                request.getClientName());

                rfh.setPositionTitle(
                                request.getPositionTitle());

                rfh.setLocation(
                                request.getLocation());

                rfh.setLocationPreferred(
                                request.getLocationPreferred());

                rfh.setBusiness(
                                request.getBusiness());

                rfh.setVertical(
                                request.getVertical());

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

                rfh.setEmpCategory(
                                request.getEmpCategory());

                rfh.setType(
                                request.getType());

                rfh.setAnySpecific(
                                request.getAnySpecific());

                rfh.setDeleteRemark(
                                request.getDeleteRemark());

                rfh.setApprovalHirePath(
                                request.getApprovalHirePath());

                rfh.setApproveDate(
                                request.getApproveDate());

                rfh.setDepartment(
                                request.getDepartment());

                rfh.setDesignation(
                                request.getDesignation());

                rfh.setTenDoj(
                                request.getTenDoj());

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

                Rfh updated = rfhRepository.save(rfh);

                return mapToResponse(updated);
        }

        // =========================================================
        // DELETE
        // =========================================================

        @Override
        public void delete(
                        Long id,
                        String remark) {

                Rfh rfh = rfhRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "RFH not found: " + id));

                rfh.setDeleteStatus(1);

                rfh.setDeleteRemark(remark);

                rfhRepository.save(rfh);
        }

        // =========================================================
        // ENTITY -> RESPONSE
        // =========================================================

        private RfhResponse mapToResponse(
                        Rfh rfh) {

                return new RfhResponse(

                                rfh.getId(),
                                rfh.getResId(),
                                rfh.getTicketNumber(),
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
                                rfh.getPositionTitle(),
                                rfh.getLocation(),
                                rfh.getLocationPreferred(),
                                rfh.getBusiness(),
                                rfh.getVertical(),
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
                                rfh.getEmpCategory(),
                                rfh.getType(),
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
                                rfh.getTenDoj(),
                                rfh.getAttendanceFormat(),
                                rfh.getWeekOff(),
                                rfh.getCkSupervisior(),
                                rfh.getCkMail(),
                                rfh.getApproverId(),
                                rfh.getReporterId(),
                                rfh.getClientName());
        }
}