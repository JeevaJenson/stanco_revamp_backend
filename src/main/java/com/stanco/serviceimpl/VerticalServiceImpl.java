package com.stanco.serviceimpl;

import com.stanco.dto.request.VerticalRequest;
import com.stanco.dto.response.VerticalResponse;

import com.stanco.entity.Vertical;

import com.stanco.enums.Status;

import com.stanco.repository.VerticalRepository;

import com.stanco.service.VerticalService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VerticalServiceImpl
                implements VerticalService {

        private final VerticalRepository repository;

        @Override
        public VerticalResponse create(
                        VerticalRequest request,
                        String createdBy) {

                String name = request.getVerticalName().trim();

                if (repository.existsByVerticalName(name)) {

                        throw new RuntimeException(
                                        "Vertical already exists: "
                                                        + name);
                }

                Vertical vertical = new Vertical();

                vertical.setVerticalName(name);

                vertical.setStatus(
                                request.getStatus() != null
                                                ? request.getStatus()
                                                : Status.active);

                vertical.setCreatedBy(createdBy);

                vertical.setCreatedAt(
                                LocalDateTime.now());

                vertical.setUpdatedAt(
                                LocalDateTime.now());

                Vertical saved = repository.save(vertical);

                return mapToResponse(saved);
        }

        @Override
        public List<VerticalResponse> getAll() {

                return repository.findAll()
                                .stream()
                                .map(this::mapToResponse)
                                .toList();
        }

        @Override
        public VerticalResponse getById(Long id) {

                Vertical vertical = repository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Vertical not found: "
                                                                + id));

                return mapToResponse(vertical);
        }

        @Override
        public VerticalResponse getByName(
                        String verticalName) {

                if (verticalName == null ||
                                verticalName.trim().isEmpty()) {

                        throw new RuntimeException(
                                        "Vertical name is required");
                }

                Vertical vertical = repository
                                .findByVerticalName(
                                                verticalName.trim())
                                .orElseThrow(() -> new RuntimeException(
                                                "Vertical not found: "
                                                                + verticalName));

                return mapToResponse(vertical);
        }

        @Override
        public VerticalResponse update(
                        Long id,
                        VerticalRequest request,
                        String updatedBy) {

                Vertical vertical = repository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Vertical not found: "
                                                                + id));

                String newName = request.getVerticalName().trim();

                if (!newName.equals(
                                vertical.getVerticalName())
                                &&
                                repository.existsByVerticalName(
                                                newName)) {

                        throw new RuntimeException(
                                        "Vertical already exists: "
                                                        + newName);
                }

                vertical.setVerticalName(newName);

                if (request.getStatus() != null) {

                        vertical.setStatus(
                                        request.getStatus());

                        if (request.getStatus() == Status.active) {

                                vertical.setDeletedAt(null);

                                vertical.setDeletedBy(null);
                        }

                        if (request.getStatus() == Status.inactive) {

                                vertical.setDeletedAt(
                                                LocalDateTime.now());

                                vertical.setDeletedBy(
                                                updatedBy);
                        }
                }

                vertical.setUpdatedBy(updatedBy);

                vertical.setUpdatedAt(
                                LocalDateTime.now());

                Vertical updated = repository.save(vertical);

                return mapToResponse(updated);
        }

        @Override
        public void delete(
                        Long id,
                        String deletedBy) {

                Vertical vertical = repository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Vertical not found: "
                                                                + id));

                vertical.setStatus(
                                Status.inactive);

                vertical.setDeletedBy(
                                deletedBy);

                vertical.setDeletedAt(
                                LocalDateTime.now());

                vertical.setUpdatedBy(
                                deletedBy);

                vertical.setUpdatedAt(
                                LocalDateTime.now());

                repository.save(vertical);
        }

        private VerticalResponse mapToResponse(
                        Vertical vertical) {

                return new VerticalResponse(

                                vertical.getId(),

                                vertical.getVerticalName(),

                                vertical.getStatus(),

                                vertical.getCreatedBy(),

                                vertical.getUpdatedBy(),

                                vertical.getDeletedBy(),

                                vertical.getCreatedAt(),

                                vertical.getUpdatedAt(),

                                vertical.getDeletedAt());
        }
}