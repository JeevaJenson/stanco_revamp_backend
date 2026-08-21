package com.stanco.serviceimpl;

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

                // -----------------------------------------------------
                // CHECK DUPLICATE REC REQ ID
                // -----------------------------------------------------

                if (repository.existsByRecReqID(
                                request.getRecReqID())) {

                        throw new RuntimeException(
                                        "Recruitment Request ID already exists: "
                                                        + request.getRecReqID());
                }

                // -----------------------------------------------------
                // CREATE ENTITY
                // -----------------------------------------------------

                RecruitmentRequest recruitmentRequest = new RecruitmentRequest();

                recruitmentRequest.setRecReqID(
                                request.getRecReqID());

                recruitmentRequest.setRfhNo(
                                request.getRfhNo());

                recruitmentRequest.setPositionTitle(
                                request.getPositionTitle());

                recruitmentRequest.setNoOfPosition(
                                request.getNoOfPosition());

                recruitmentRequest.setBand(
                                request.getBand());

                recruitmentRequest.setOpenDate(
                                request.getOpenDate());

                recruitmentRequest.setCriticalPosition(
                                request.getCriticalPosition());

                recruitmentRequest.setBusiness(
                                request.getBusiness());

                recruitmentRequest.setDivision(
                                request.getDivision());

                recruitmentRequest.setFunction(
                                request.getFunction());

                recruitmentRequest.setLocation(
                                request.getLocation());

                recruitmentRequest.setBillingStatus(
                                request.getBillingStatus());

                recruitmentRequest.setInterviewer(
                                request.getInterviewer());

                recruitmentRequest.setSalaryRange(
                                request.getSalaryRange());

                recruitmentRequest.setSalaryRangeAnnual(
                                request.getSalaryRangeAnnual());

                recruitmentRequest.setRequestStatus(
                                request.getRequestStatus());

                recruitmentRequest.setCloseDate(
                                request.getCloseDate());

                // =====================================================
                // ALLOCATION
                // =====================================================

                recruitmentRequest.setAssignedStatus(
                                "Unassigned");

                recruitmentRequest.setAssignedTo(
                                null);

                recruitmentRequest.setAssignedDate(
                                null);

                // =====================================================
                // OTHER FIELDS
                // =====================================================

                recruitmentRequest.setHeplRecruitmentRefNumber(
                                request.getHeplRecruitmentRefNumber());

                recruitmentRequest.setActionForTheDayStatus(
                                request.getActionForTheDayStatus());

                recruitmentRequest.setCreatedBy(
                                createdBy);

                recruitmentRequest.setModifiedBy(
                                null);

                recruitmentRequest.setCreatedAt(
                                LocalDateTime.now());

                recruitmentRequest.setUpdatedAt(
                                LocalDateTime.now());

                recruitmentRequest.setDeleteStatus(
                                0);

                recruitmentRequest.setSubPositionTitle(
                                request.getSubPositionTitle());

                recruitmentRequest.setClosedBy(
                                request.getClosedBy());

                // =====================================================
                // SAVE
                // =====================================================

                RecruitmentRequest saved = repository.save(
                                recruitmentRequest);

                return mapToResponse(saved);
        }

        // =========================================================
        // GET ALL
        // =========================================================

        @Override
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
        public RecruitmentRequestResponse getById(
                        Long id) {

                RecruitmentRequest recruitmentRequest = repository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Recruitment Request not found: "
                                                                + id));

                // -----------------------------------------------------
                // DO NOT RETURN DELETED RECORD
                // -----------------------------------------------------

                if (recruitmentRequest.getDeleteStatus() != null
                                && recruitmentRequest.getDeleteStatus() == 1) {

                        throw new RuntimeException(
                                        "Recruitment Request is deleted: "
                                                        + id);
                }

                return mapToResponse(
                                recruitmentRequest);
        }

        // =========================================================
        // GET BY REC REQ ID
        // =========================================================

        @Override
        public RecruitmentRequestResponse getByRecReqID(
                        String recReqID) {

                RecruitmentRequest recruitmentRequest = repository.findByRecReqID(
                                recReqID)
                                .orElseThrow(() -> new RuntimeException(
                                                "Recruitment Request not found: "
                                                                + recReqID));

                if (recruitmentRequest.getDeleteStatus() != null
                                && recruitmentRequest.getDeleteStatus() == 1) {

                        throw new RuntimeException(
                                        "Recruitment Request is deleted: "
                                                        + recReqID);
                }

                return mapToResponse(
                                recruitmentRequest);
        }

        // =========================================================
        // GET MY REQUESTS
        // =========================================================

        @Override
        public List<RecruitmentRequestResponse> getMyRequests(
                        String empID) {

                return repository
                                .findByAssignedTo(empID)
                                .stream()
                                .filter(request -> request.getDeleteStatus() == null
                                                || request.getDeleteStatus() == 0)
                                .filter(request -> "Assigned".equalsIgnoreCase(
                                                request.getAssignedStatus()))
                                .map(this::mapToResponse)
                                .toList();
        }

        // =========================================================
        // UPDATE
        // =========================================================

        @Override
        public RecruitmentRequestResponse update(
                        Long id,
                        RecruitmentRequestRequest request,
                        String modifiedBy) {

                // -----------------------------------------------------
                // FIND EXISTING RECORD
                // -----------------------------------------------------

                RecruitmentRequest existing = repository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Recruitment Request not found: "
                                                                + id));

                // -----------------------------------------------------
                // PREVENT UPDATE OF DELETED RECORD
                // -----------------------------------------------------

                if (existing.getDeleteStatus() != null
                                && existing.getDeleteStatus() == 1) {

                        throw new RuntimeException(
                                        "Cannot update deleted Recruitment Request: "
                                                        + id);
                }

                // =====================================================
                // BASIC DATA
                // =====================================================

                existing.setRecReqID(
                                request.getRecReqID());

                existing.setRfhNo(
                                request.getRfhNo());

                existing.setPositionTitle(
                                request.getPositionTitle());

                existing.setNoOfPosition(
                                request.getNoOfPosition());

                existing.setBand(
                                request.getBand());

                existing.setOpenDate(
                                request.getOpenDate());

                existing.setCriticalPosition(
                                request.getCriticalPosition());

                existing.setBusiness(
                                request.getBusiness());

                existing.setDivision(
                                request.getDivision());

                existing.setFunction(
                                request.getFunction());

                existing.setLocation(
                                request.getLocation());

                existing.setBillingStatus(
                                request.getBillingStatus());

                existing.setInterviewer(
                                request.getInterviewer());

                existing.setSalaryRange(
                                request.getSalaryRange());

                existing.setSalaryRangeAnnual(
                                request.getSalaryRangeAnnual());

                existing.setRequestStatus(
                                request.getRequestStatus());

                existing.setCloseDate(
                                request.getCloseDate());

                // =====================================================
                // ALLOCATION DATA
                // =====================================================

                /*
                 * IMPORTANT:
                 *
                 * Allocate button frontend sends:
                 *
                 * assignedStatus = Assigned
                 * assignedTo = recruiter ID
                 * assignedDate = date
                 *
                 * So these values are updated here.
                 */

                if (request.getAssignedStatus() != null
                                && !request
                                                .getAssignedStatus()
                                                .trim()
                                                .isEmpty()) {

                        existing.setAssignedStatus(
                                        request.getAssignedStatus());

                }

                if (request.getAssignedTo() != null
                                && !request
                                                .getAssignedTo()
                                                .trim()
                                                .isEmpty()) {

                        existing.setAssignedTo(
                                        request.getAssignedTo());

                }

                if (request.getAssignedDate() != null
                                && !request
                                                .getAssignedDate()
                                                .trim()
                                                .isEmpty()) {

                        existing.setAssignedDate(
                                        request.getAssignedDate());

                }

                // =====================================================
                // OTHER DATA
                // =====================================================

                existing.setHeplRecruitmentRefNumber(
                                request.getHeplRecruitmentRefNumber());

                existing.setActionForTheDayStatus(
                                request.getActionForTheDayStatus());

                existing.setSubPositionTitle(
                                request.getSubPositionTitle());

                existing.setClosedBy(
                                request.getClosedBy());

                // =====================================================
                // AUDIT
                // =====================================================

                existing.setModifiedBy(
                                modifiedBy);

                existing.setUpdatedAt(
                                LocalDateTime.now());

                // =====================================================
                // SAVE
                // =====================================================

                RecruitmentRequest updated = repository.save(existing);

                return mapToResponse(updated);
        }

        // =========================================================
        // DELETE
        // =========================================================

        @Override
        public void delete(
                        Long id,
                        String modifiedBy) {

                RecruitmentRequest recruitmentRequest = repository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Recruitment Request not found: "
                                                                + id));

                // -----------------------------------------------------
                // SOFT DELETE
                // -----------------------------------------------------

                recruitmentRequest.setDeleteStatus(1);

                recruitmentRequest.setModifiedBy(
                                modifiedBy);

                recruitmentRequest.setUpdatedAt(
                                LocalDateTime.now());

                repository.save(
                                recruitmentRequest);
        }

        // =========================================================
        // ENTITY → RESPONSE
        // =========================================================

        private RecruitmentRequestResponse mapToResponse(
                        RecruitmentRequest request) {

                return new RecruitmentRequestResponse(

                                request.getId(),

                                request.getRecReqID(),

                                request.getRfhNo(),

                                request.getPositionTitle(),

                                request.getNoOfPosition(),

                                request.getBand(),

                                request.getOpenDate(),

                                request.getCriticalPosition(),

                                request.getBusiness(),

                                request.getDivision(),

                                request.getFunction(),

                                request.getLocation(),

                                request.getBillingStatus(),

                                request.getInterviewer(),

                                request.getSalaryRange(),

                                request.getSalaryRangeAnnual(),

                                request.getRequestStatus(),

                                request.getCloseDate(),

                                request.getAssignedStatus(),

                                request.getAssignedTo(),

                                request.getAssignedDate(),

                                request.getHeplRecruitmentRefNumber(),

                                request.getActionForTheDayStatus(),

                                request.getCreatedBy(),

                                request.getModifiedBy(),

                                request.getCreatedAt(),

                                request.getUpdatedAt(),

                                request.getDeleteStatus(),

                                request.getSubPositionTitle(),

                                request.getClosedBy());
        }
}