package com.stanco.serviceimpl;

import com.stanco.dto.request.CandidatePreonboardingRequest;
import com.stanco.dto.response.CandidatePreonboardingResponse;

import com.stanco.entity.CandidatePreonboarding;

import com.stanco.repository.CandidatePreonboardingRepository;

import com.stanco.service.CandidatePreonboardingService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CandidatePreonboardingServiceImpl
                implements CandidatePreonboardingService {

        private final CandidatePreonboardingRepository repository;

        // =========================================================
        // CREATE
        // =========================================================

        @Override
        public CandidatePreonboardingResponse create(
                        CandidatePreonboardingRequest request) {

                CandidatePreonboarding record = new CandidatePreonboarding();

                record.setEmpId(
                                request.getEmpId());

                record.setRecruiterId(
                                request.getRecruiterId());

                record.setPreonboardingProcess(
                                request.getPreonboardingProcess());

                record.setType(
                                request.getType());

                record.setDate(
                                request.getDate());

                LocalDateTime now = LocalDateTime.now();

                record.setCreatedAt(now);

                record.setUpdatedAt(now);

                CandidatePreonboarding saved = repository.save(record);

                return mapToResponse(saved);
        }

        // =========================================================
        // GET ALL
        // =========================================================

        @Override
        public List<CandidatePreonboardingResponse> getAll() {

                return repository
                                .findAll()
                                .stream()
                                .map(this::mapToResponse)
                                .toList();
        }

        // =========================================================
        // GET BY ID
        // =========================================================

        @Override
        public CandidatePreonboardingResponse getById(
                        Long id) {

                CandidatePreonboarding record = repository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Pre-onboarding record not found: "
                                                                + id));

                return mapToResponse(record);
        }

        // =========================================================
        // GET BY EMPLOYEE
        // =========================================================

        @Override
        public List<CandidatePreonboardingResponse> getByEmpId(
                        String empId) {

                return repository
                                .findByEmpId(empId)
                                .stream()
                                .map(this::mapToResponse)
                                .toList();
        }

        // =========================================================
        // GET BY RECRUITER
        // =========================================================

        @Override
        public List<CandidatePreonboardingResponse> getByRecruiterId(
                        String recruiterId) {

                return repository
                                .findByRecruiterId(recruiterId)
                                .stream()
                                .map(this::mapToResponse)
                                .toList();
        }

        // =========================================================
        // GET BY PROCESS
        // =========================================================

        @Override
        public List<CandidatePreonboardingResponse> getByPreonboardingProcess(
                        String process) {

                return repository
                                .findByPreonboardingProcess(process)
                                .stream()
                                .map(this::mapToResponse)
                                .toList();
        }

        // =========================================================
        // GET BY TYPE
        // =========================================================

        @Override
        public List<CandidatePreonboardingResponse> getByType(
                        Integer type) {

                return repository
                                .findByType(type)
                                .stream()
                                .map(this::mapToResponse)
                                .toList();
        }

        // =========================================================
        // UPDATE
        // =========================================================

        @Override
        public CandidatePreonboardingResponse update(
                        Long id,
                        CandidatePreonboardingRequest request) {

                CandidatePreonboarding record = repository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Pre-onboarding record not found: "
                                                                + id));

                record.setEmpId(
                                request.getEmpId());

                record.setRecruiterId(
                                request.getRecruiterId());

                record.setPreonboardingProcess(
                                request.getPreonboardingProcess());

                record.setType(
                                request.getType());

                record.setDate(
                                request.getDate());

                record.setUpdatedAt(
                                LocalDateTime.now());

                CandidatePreonboarding updated = repository.save(record);

                return mapToResponse(updated);
        }

        // =========================================================
        // DELETE
        // =========================================================

        @Override
        public void delete(Long id) {

                CandidatePreonboarding record = repository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Pre-onboarding record not found: "
                                                                + id));

                repository.delete(record);
        }

        // =========================================================
        // MAPPING
        // =========================================================

        private CandidatePreonboardingResponse mapToResponse(
                        CandidatePreonboarding record) {

                return new CandidatePreonboardingResponse(

                                record.getId(),

                                record.getEmpId(),

                                record.getRecruiterId(),

                                record.getPreonboardingProcess(),

                                record.getType(),

                                record.getDate(),

                                record.getCreatedAt(),

                                record.getUpdatedAt());
        }
}