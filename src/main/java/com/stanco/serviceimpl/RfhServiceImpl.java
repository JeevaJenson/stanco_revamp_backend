package com.stanco.serviceimpl;

import com.stanco.dto.request.RfhRequest;
import com.stanco.dto.response.RfhResponse;
import com.stanco.entity.Rfh;
import com.stanco.repository.RfhRepository;
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

        // =========================================================
        // CREATE RFH
        // =========================================================

        @Override
        public RfhResponse create(
                        RfhRequest request,
                        String requestBy) {

                // =====================================================
                // CREATE ENTITY
                // =====================================================

                Rfh rfh = new Rfh();

                // =====================================================
                // AUTO GENERATE RES ID
                // Example: RES0001
                // =====================================================

                rfh.setResId(generateResId());

                // =====================================================
                // AUTO GENERATE RFH NUMBER
                // ticketNumber stores RFH Number
                // Example: RFH0001
                // =====================================================

                rfh.setTicketNumber(generateRfhNumber());

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
                // REQUEST DETAILS
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
                // SAVE
                // =====================================================

                Rfh saved = repository.save(rfh);

                return mapToResponse(saved);
        }

        // =========================================================
        // GENERATE RES ID
        // Example:
        // RES0001
        // RES0002
        // RES0003
        // =========================================================

        private String generateResId() {

                long number = repository.count() + 1;

                String resId = "RES" + String.format("%02d", number);

                while (repository.existsByResId(resId)) {

                        number++;

                        resId = "RES" + String.format("%02d", number);
                }

                return resId;
        }

        // =========================================================
        // GENERATE RFH NUMBER
        // Example:
        // RFH0001
        // RFH0002
        // RFH0003
        //
        // Stored in ticketNumber column
        // =========================================================

        private String generateRfhNumber() {

                long number = repository.count() + 1;

                String rfhNumber = "RFH" + String.format("%04d", number);

                while (repository.existsByTicketNumber(rfhNumber)) {

                        number++;

                        rfhNumber = "RFH" + String.format("%04d", number);
                }

                return rfhNumber;
        }

        // =========================================================
        // GET ALL RFH
        // =========================================================

        @Override
        @Transactional(readOnly = true)
        public List<RfhResponse> getAll() {

                return repository.findAll()
                                .stream()
                                .filter(rfh -> rfh.getDeleteStatus() == null
                                                || rfh.getDeleteStatus() == 0)
                                .map(this::mapToResponse)
                                .toList();
        }

        // =========================================================
        // GET BY ID
        // =========================================================

        @Override
        @Transactional(readOnly = true)
        public RfhResponse getById(Long id) {

                Rfh rfh = repository.findById(id)
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

                Rfh rfh = repository
                                .findByResId(resId)
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

                return repository
                                .findByRequestBy(requestBy)
                                .stream()
                                .filter(rfh -> rfh.getDeleteStatus() == null
                                                || rfh.getDeleteStatus() == 0)
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

                Rfh rfh = repository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "RFH not found: " + id));

                // =====================================================
                // DO NOT CHANGE AUTO GENERATED VALUES
                // =====================================================

                // RES ID remains same
                // RFH Number remains same

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
                // REQUEST DETAILS
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
                // OTHER FIELDS
                // =====================================================

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

                // =====================================================
                // SAVE UPDATED RFH
                // =====================================================

                Rfh updated = repository.save(rfh);

                return mapToResponse(updated);
        }

        // =========================================================
        // DELETE RFH
        // SOFT DELETE
        // =========================================================

        @Override
        public void delete(
                        Long id,
                        String remark) {

                Rfh rfh = repository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "RFH not found: " + id));

                rfh.setDeleteStatus(1);

                rfh.setDeleteRemark(remark);

                repository.save(rfh);
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