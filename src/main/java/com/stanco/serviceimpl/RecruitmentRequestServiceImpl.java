package com.stanco.serviceimpl;

import com.stanco.dto.request.RecruitmentAllocationRequest;
import com.stanco.dto.request.RecruitmentRequestRequest;
import com.stanco.dto.response.RecruitmentRequestResponse;
import com.stanco.entity.RecruitmentRequest;
import com.stanco.repository.RecruitmentRequestRepository;
import com.stanco.service.RecruitmentRequestService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RecruitmentRequestServiceImpl
                implements RecruitmentRequestService {

        private final RecruitmentRequestRepository repository;

        // =========================================================
        // CREATE
        // =========================================================

        @Override
        public RecruitmentRequestResponse create(
                        RecruitmentRequestRequest request,
                        String createdBy) {

                if (request.getRecReqID() != null
                                && repository.existsByRecReqID(
                                                request.getRecReqID())) {

                        throw new RuntimeException(
                                        "Recruitment Request ID already exists: "
                                                        + request.getRecReqID());
                }

                RecruitmentRequest entity = new RecruitmentRequest();

                // =====================================================
                // BASIC DETAILS
                // =====================================================

                entity.setRecReqID(
                                request.getRecReqID());

                entity.setRfhNo(
                                request.getRfhNo());

                entity.setPositionTitle(
                                request.getPositionTitle());

                entity.setNoOfPosition(
                                request.getNoOfPosition());

                entity.setBand(
                                request.getBand());

                entity.setOpenDate(
                                request.getOpenDate());

                entity.setCriticalPosition(
                                request.getCriticalPosition());

                // =====================================================
                // ORGANIZATION
                // =====================================================

                entity.setBusiness(
                                request.getBusiness());

                entity.setDivision(
                                request.getDivision());

                entity.setFunction(
                                request.getFunction());

                entity.setLocation(
                                request.getLocation());

                // =====================================================
                // OTHER DETAILS
                // =====================================================

                entity.setBillingStatus(
                                request.getBillingStatus());

                entity.setInterviewer(
                                request.getInterviewer());

                entity.setSalaryRange(
                                request.getSalaryRange());

                entity.setSalaryRangeAnnual(
                                request.getSalaryRangeAnnual());

                entity.setRequestStatus(
                                request.getRequestStatus() != null
                                                ? request.getRequestStatus()
                                                : "OPEN");

                entity.setCloseDate(
                                request.getCloseDate());

                entity.setHeplRecruitmentRefNumber(
                                request.getHeplRecruitmentRefNumber());

                entity.setActionForTheDayStatus(
                                request.getActionForTheDayStatus());

                entity.setSubPositionTitle(
                                request.getSubPositionTitle());

                entity.setClosedBy(
                                request.getClosedBy());

                // =====================================================
                // ALLOCATION INITIAL STATE
                // =====================================================

                entity.setAssignedStatus(
                                "Unassigned");

                entity.setAssignedTo(null);

                entity.setAssignedDate(null);

                // =====================================================
                // AUDIT
                // =====================================================

                entity.setCreatedBy(
                                createdBy);

                entity.setModifiedBy(
                                createdBy);

                entity.setCreatedAt(
                                LocalDateTime.now());

                entity.setUpdatedAt(
                                LocalDateTime.now());

                entity.setDeleteStatus(0);

                // =====================================================
                // SAVE
                // =====================================================

                RecruitmentRequest saved = repository.save(entity);

                return mapToResponse(saved);
        }

        // =========================================================
        // GET ALL
        // =========================================================

        @Override
        @Transactional(readOnly = true)
        public List<RecruitmentRequestResponse> getAll() {

                return repository
                                .findByDeleteStatus(0)
                                .stream()
                                .map(this::mapToResponse)
                                .toList();
        }

        // =========================================================
        // GET BY ID
        // =========================================================

        @Override
        @Transactional(readOnly = true)
        public RecruitmentRequestResponse getById(
                        Long id) {

                RecruitmentRequest entity = repository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Recruitment Request not found: "
                                                                + id));

                return mapToResponse(entity);
        }

        // =========================================================
        // GET BY REC REQ ID
        // =========================================================

        @Override
        @Transactional(readOnly = true)
        public RecruitmentRequestResponse getByRecReqID(
                        String recReqID) {

                RecruitmentRequest entity = repository.findByRecReqID(recReqID)
                                .orElseThrow(() -> new RuntimeException(
                                                "Recruitment Request not found: "
                                                                + recReqID));

                return mapToResponse(entity);
        }

        // =========================================================
        // GET MY ALLOCATED REQUESTS
        // =========================================================

        @Override
        @Transactional(readOnly = true)
        public List<RecruitmentRequestResponse> getMyRequests(
                        String empID) {

                return repository
                                .findByAssignedToAndAssignedStatus(
                                                empID,
                                                "Assigned")
                                .stream()
                                .filter(this::isActive)
                                .map(this::mapToResponse)
                                .toList();
        }

        // =========================================================
        // GET ALL ALLOCATED
        // =========================================================

        @Override
        @Transactional(readOnly = true)
        public List<RecruitmentRequestResponse> getAllocatedRequests() {

                return repository
                                .findByDeleteStatus(0)
                                .stream()
                                .filter(entity -> "Assigned".equalsIgnoreCase(
                                                entity.getAssignedStatus()))
                                .map(this::mapToResponse)
                                .toList();
        }

        // =========================================================
        // GET UNALLOCATED
        // =========================================================

        @Override
        @Transactional(readOnly = true)
        public List<RecruitmentRequestResponse> getUnallocatedRequests() {

                return repository
                                .findByDeleteStatus(0)
                                .stream()
                                .filter(entity ->

                                entity.getAssignedStatus() == null

                                                ||

                                                "Unassigned".equalsIgnoreCase(
                                                                entity.getAssignedStatus())

                                )
                                .map(this::mapToResponse)
                                .toList();
        }

        // =========================================================
        // NORMAL UPDATE
        // =========================================================

        @Override
        public RecruitmentRequestResponse update(
                        Long id,
                        RecruitmentRequestRequest request,
                        String modifiedBy) {

                RecruitmentRequest entity = repository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Recruitment Request not found: "
                                                                + id));

                // =====================================================
                // BASIC DETAILS
                // =====================================================

                entity.setRecReqID(
                                request.getRecReqID());

                entity.setRfhNo(
                                request.getRfhNo());

                entity.setPositionTitle(
                                request.getPositionTitle());

                entity.setNoOfPosition(
                                request.getNoOfPosition());

                entity.setBand(
                                request.getBand());

                entity.setOpenDate(
                                request.getOpenDate());

                entity.setCriticalPosition(
                                request.getCriticalPosition());

                // =====================================================
                // ORGANIZATION
                // =====================================================

                entity.setBusiness(
                                request.getBusiness());

                entity.setDivision(
                                request.getDivision());

                entity.setFunction(
                                request.getFunction());

                entity.setLocation(
                                request.getLocation());

                // =====================================================
                // OTHER DETAILS
                // =====================================================

                entity.setBillingStatus(
                                request.getBillingStatus());

                entity.setInterviewer(
                                request.getInterviewer());

                entity.setSalaryRange(
                                request.getSalaryRange());

                entity.setSalaryRangeAnnual(
                                request.getSalaryRangeAnnual());

                entity.setRequestStatus(
                                request.getRequestStatus());

                entity.setCloseDate(
                                request.getCloseDate());

                entity.setHeplRecruitmentRefNumber(
                                request.getHeplRecruitmentRefNumber());

                entity.setActionForTheDayStatus(
                                request.getActionForTheDayStatus());

                entity.setSubPositionTitle(
                                request.getSubPositionTitle());

                entity.setClosedBy(
                                request.getClosedBy());

                // =====================================================
                // IMPORTANT
                // =====================================================
                // Do NOT update:
                //
                // assignedStatus
                // assignedTo
                // assignedDate
                //
                // Allocation is handled only by allocate().

                entity.setModifiedBy(
                                modifiedBy);

                entity.setUpdatedAt(
                                LocalDateTime.now());

                RecruitmentRequest updated = repository.save(entity);

                return mapToResponse(updated);
        }

        // =========================================================
        // ALLOCATE RECRUITER
        // =========================================================

        @Override
        public RecruitmentRequestResponse allocate(
                        Long id,
                        RecruitmentAllocationRequest request,
                        String modifiedBy) {

                RecruitmentRequest entity = repository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Recruitment Request not found: "
                                                                + id));

                // =====================================================
                // DELETED CHECK
                // =====================================================

                if (entity.getDeleteStatus() != null
                                && entity.getDeleteStatus() == 1) {

                        throw new RuntimeException(
                                        "Cannot allocate deleted recruitment request");
                }

                // =====================================================
                // RECRUITER ID
                // =====================================================

                if (request.getAssignedTo() == null
                                || request.getAssignedTo()
                                                .trim()
                                                .isEmpty()) {

                        throw new RuntimeException(
                                        "Recruiter ID is required");
                }

                // =====================================================
                // DATE
                // =====================================================

                if (request.getAssignedDate() == null
                                || request.getAssignedDate()
                                                .trim()
                                                .isEmpty()) {

                        throw new RuntimeException(
                                        "Allocation date is required");
                }

                // =====================================================
                // UPDATE ALLOCATION
                // =====================================================

                entity.setAssignedStatus(
                                "Assigned");

                entity.setAssignedTo(
                                request.getAssignedTo()
                                                .trim());

                entity.setAssignedDate(
                                request.getAssignedDate()
                                                .trim());

                entity.setModifiedBy(
                                modifiedBy);

                entity.setUpdatedAt(
                                LocalDateTime.now());

                // =====================================================
                // SAVE TO DATABASE
                // =====================================================

                RecruitmentRequest saved = repository.save(entity);

                return mapToResponse(saved);
        }

        // =========================================================
        // DELETE
        // =========================================================

        @Override
        public void delete(
                        Long id,
                        String modifiedBy) {

                RecruitmentRequest entity = repository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Recruitment Request not found: "
                                                                + id));

                entity.setDeleteStatus(1);

                entity.setModifiedBy(
                                modifiedBy);

                entity.setUpdatedAt(
                                LocalDateTime.now());

                repository.save(entity);
        }

        // =========================================================
        // ACTIVE CHECK
        // =========================================================

        private boolean isActive(
                        RecruitmentRequest entity) {

                return entity.getDeleteStatus() == null
                                || entity.getDeleteStatus() == 0;
        }

        // =========================================================
        // ENTITY -> RESPONSE
        // =========================================================

        private RecruitmentRequestResponse mapToResponse(
                        RecruitmentRequest entity) {

                return new RecruitmentRequestResponse(

                                entity.getId(),

                                entity.getRecReqID(),

                                entity.getRfhNo(),

                                entity.getPositionTitle(),

                                entity.getNoOfPosition(),

                                entity.getBand(),

                                entity.getOpenDate(),

                                entity.getCriticalPosition(),

                                entity.getBusiness(),

                                entity.getDivision(),

                                entity.getFunction(),

                                entity.getLocation(),

                                entity.getBillingStatus(),

                                entity.getInterviewer(),

                                entity.getSalaryRange(),

                                entity.getSalaryRangeAnnual(),

                                entity.getRequestStatus(),

                                entity.getCloseDate(),

                                // Allocation
                                entity.getAssignedStatus(),

                                entity.getAssignedTo(),

                                entity.getAssignedDate(),

                                entity.getHeplRecruitmentRefNumber(),

                                entity.getActionForTheDayStatus(),

                                // Audit
                                entity.getCreatedBy(),

                                entity.getModifiedBy(),

                                entity.getCreatedAt(),

                                entity.getUpdatedAt(),

                                entity.getDeleteStatus(),

                                entity.getSubPositionTitle(),

                                entity.getClosedBy());
        }
}