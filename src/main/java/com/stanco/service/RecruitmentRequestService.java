package com.stanco.service;

import com.stanco.dto.request.RecruitmentAllocationRequest;
import com.stanco.dto.request.RecruitmentRequestRequest;
import com.stanco.dto.response.RecruitmentRequestResponse;

import java.util.List;

public interface RecruitmentRequestService {

        // =====================================================
        // CREATE
        // =====================================================

        RecruitmentRequestResponse create(
                        RecruitmentRequestRequest request,
                        String createdBy);

        // =====================================================
        // GET ALL
        // =====================================================

        List<RecruitmentRequestResponse> getAll();

        // =====================================================
        // GET BY ID
        // =====================================================

        RecruitmentRequestResponse getById(
                        Long id);

        // =====================================================
        // GET BY REC REQ ID
        // =====================================================

        RecruitmentRequestResponse getByRecReqID(
                        String recReqID);

        // =====================================================
        // GET MY ALLOCATED REQUESTS
        // Logged-in recruiter records only
        // =====================================================

        List<RecruitmentRequestResponse> getMyRequests(
                        String empID);

        // =====================================================
        // GET ALL ALLOCATED
        // =====================================================

        List<RecruitmentRequestResponse> getAllocatedRequests();

        // =====================================================
        // GET ALL UNALLOCATED
        // =====================================================

        List<RecruitmentRequestResponse> getUnallocatedRequests();

        // =====================================================
        // NORMAL UPDATE
        // =====================================================

        RecruitmentRequestResponse update(
                        Long id,
                        RecruitmentRequestRequest request,
                        String modifiedBy);

        // =====================================================
        // ALLOCATE RECRUITER
        // =====================================================

        RecruitmentRequestResponse allocate(
                        Long id,
                        RecruitmentAllocationRequest request,
                        String modifiedBy);

        // =====================================================
        // DELETE
        // =====================================================

        void delete(
                        Long id,
                        String modifiedBy);
}