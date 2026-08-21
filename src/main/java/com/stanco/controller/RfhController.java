package com.stanco.controller;

import com.stanco.dto.request.RfhRequest;
import com.stanco.dto.response.RfhResponse;
import com.stanco.service.RfhService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rfh")
@RequiredArgsConstructor
public class RfhController {

        private final RfhService service;

        // =====================================================
        // CREATE
        // =====================================================

        @PostMapping
        public ResponseEntity<RfhResponse> create(
                        @Valid @RequestBody RfhRequest request,
                        Authentication authentication) {

                String empID = authentication.getName();

                return ResponseEntity.ok(
                                service.create(
                                                request,
                                                empID));
        }

        // =====================================================
        // GET ALL
        // =====================================================

        @GetMapping
        public ResponseEntity<List<RfhResponse>> getAll() {

                return ResponseEntity.ok(
                                service.getAll());
        }

        // =====================================================
        // GET BY ID
        // =====================================================

        @GetMapping("/{id}")
        public ResponseEntity<RfhResponse> getById(
                        @PathVariable Long id) {

                return ResponseEntity.ok(
                                service.getById(id));
        }

        // =====================================================
        // GET BY RES ID
        // =====================================================

        @GetMapping("/res/{resId}")
        public ResponseEntity<RfhResponse> getByResId(
                        @PathVariable String resId) {

                return ResponseEntity.ok(
                                service.getByResId(resId));
        }

        // =====================================================
        // GET MY RFH
        // =====================================================

        @GetMapping("/my")
        public ResponseEntity<List<RfhResponse>> getMyRfh(
                        Authentication authentication) {

                String empID = authentication.getName();

                return ResponseEntity.ok(
                                service.getMyRfh(empID));
        }

        // =====================================================
        // UPDATE
        // =====================================================

        @PutMapping("/{id}")
        public ResponseEntity<RfhResponse> update(
                        @PathVariable Long id,
                        @Valid @RequestBody RfhRequest request) {

                return ResponseEntity.ok(
                                service.update(
                                                id,
                                                request));
        }

        // =====================================================
        // DELETE
        // =====================================================

        @DeleteMapping("/{id}")
        public ResponseEntity<String> delete(
                        @PathVariable Long id,
                        @RequestParam(required = false) String remark) {

                service.delete(
                                id,
                                remark);

                return ResponseEntity.ok(
                                "RFH deleted successfully");
        }
}