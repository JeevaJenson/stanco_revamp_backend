package com.stanco.controller;

import com.stanco.dto.request.RecruitmentAllocationRequest;
import com.stanco.dto.request.RecruitmentRequestRequest;
import com.stanco.dto.response.RecruitmentRequestResponse;
import com.stanco.service.RecruitmentRequestService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruitment-requests")
@RequiredArgsConstructor
public class RecruitmentRequestController {

    private final RecruitmentRequestService service;

    // =====================================================
    // GET ALL
    // =====================================================

    @GetMapping
    public ResponseEntity<List<RecruitmentRequestResponse>> getAll() {

        return ResponseEntity.ok(
                service.getAll());
    }

    // =====================================================
    // GET ALLOCATED
    // =====================================================

    @GetMapping("/allocated")
    public ResponseEntity<List<RecruitmentRequestResponse>> getAllocated() {

        return ResponseEntity.ok(
                service.getAllocatedRequests());
    }

    // =====================================================
    // GET UNALLOCATED
    // =====================================================

    @GetMapping("/unallocated")
    public ResponseEntity<List<RecruitmentRequestResponse>> getUnallocated() {

        return ResponseEntity.ok(
                service.getUnallocatedRequests());
    }

    // =====================================================
    // GET MY ALLOCATED REQUESTS
    // =====================================================

    @GetMapping("/my")
    public ResponseEntity<List<RecruitmentRequestResponse>> getMyRequests(
            Authentication authentication) {

        String empID = authentication.getName();

        return ResponseEntity.ok(
                service.getMyRequests(empID));
    }

    // =====================================================
    // GET BY REC REQ ID
    // =====================================================

    @GetMapping("/request/{recReqID}")
    public ResponseEntity<RecruitmentRequestResponse> getByRecReqID(
            @PathVariable String recReqID) {

        return ResponseEntity.ok(
                service.getByRecReqID(recReqID));
    }

    // =====================================================
    // GET BY DATABASE ID
    // =====================================================

    @GetMapping("/{id}")
    public ResponseEntity<RecruitmentRequestResponse> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getById(id));
    }

    // =====================================================
    // CREATE
    // =====================================================

    @PostMapping
    public ResponseEntity<RecruitmentRequestResponse> create(

            @Valid @RequestBody RecruitmentRequestRequest request,

            Authentication authentication) {

        String empID = authentication.getName();

        return ResponseEntity.ok(
                service.create(
                        request,
                        empID));
    }

    // =====================================================
    // UPDATE
    // =====================================================

    @PutMapping("/{id}")
    public ResponseEntity<RecruitmentRequestResponse> update(

            @PathVariable Long id,

            @Valid @RequestBody RecruitmentRequestRequest request,

            Authentication authentication) {

        String empID = authentication.getName();

        return ResponseEntity.ok(
                service.update(
                        id,
                        request,
                        empID));
    }

    // =====================================================
    // ALLOCATE RECRUITER
    // =====================================================

    @PutMapping("/{id}/allocate")
    public ResponseEntity<RecruitmentRequestResponse> allocate(

            @PathVariable Long id,

            @Valid @RequestBody RecruitmentAllocationRequest request,

            Authentication authentication) {

        String empID = authentication.getName();

        return ResponseEntity.ok(
                service.allocate(
                        id,
                        request,
                        empID));
    }

    // =====================================================
    // DELETE
    // =====================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(

            @PathVariable Long id,

            Authentication authentication) {

        String empID = authentication.getName();

        service.delete(
                id,
                empID);

        return ResponseEntity.ok(
                "Recruitment Request deleted successfully");
    }
}