package com.stanco.serviceimpl;

import com.stanco.dto.request.CreateTeamRequest;
import com.stanco.dto.request.UpdateTeamRequest;
import com.stanco.dto.response.TeamResponse;

import com.stanco.entity.Team;
import com.stanco.entity.User;

import com.stanco.repository.TeamRepository;
import com.stanco.repository.UserRepository;

import com.stanco.service.TeamService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl
                implements TeamService {

        private final TeamRepository teamRepository;

        private final UserRepository userRepository;

        @Override
        public TeamResponse createTeam(
                        CreateTeamRequest request,
                        String creatorEmpID) {

                if (creatorEmpID == null
                                || creatorEmpID.isBlank()) {

                        throw new RuntimeException(
                                        "Creator employee ID is required");
                }

                if (request == null) {

                        throw new RuntimeException(
                                        "Team request is required");
                }

                String name = request.getName()
                                .trim();

                if (name.isBlank()) {

                        throw new RuntimeException(
                                        "Team name is required");
                }

                if (teamRepository
                                .existsByNameIgnoreCase(name)) {

                        throw new RuntimeException(
                                        "Team already exists");
                }

                Integer status = request.getStatus() == null
                                ? 1
                                : request.getStatus();

                validateStatus(status);

                Team team = new Team();

                team.setName(name);

                team.setStatus(status);

                team.setCreatedBy(
                                creatorEmpID);

                team.setUpdatedBy(
                                creatorEmpID);

                team.setDeletedAt(null);

                team.setDeletedBy(null);

                Team savedTeam = teamRepository.save(team);

                return mapToResponse(
                                savedTeam);
        }

        @Override
        public List<TeamResponse> getAllTeams() {

                return teamRepository
                                .findAllByOrderByNameAsc()
                                .stream()
                                .map(this::mapToResponse)
                                .toList();
        }

        @Override
        public List<TeamResponse> getActiveTeams() {

                return teamRepository
                                .findByStatus(1)
                                .stream()
                                .map(this::mapToResponse)
                                .toList();
        }

        @Override
        public TeamResponse getTeamById(
                        Long id) {

                Team team = teamRepository
                                .findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Team not found: "
                                                                + id));

                return mapToResponse(
                                team);
        }

        @Transactional
        @Override
        public TeamResponse updateTeam(
                        Long id,
                        UpdateTeamRequest request,
                        String updaterEmpID) {

                if (updaterEmpID == null
                                || updaterEmpID.isBlank()) {

                        throw new RuntimeException(
                                        "Updater employee ID is required");
                }

                if (request == null) {

                        throw new RuntimeException(
                                        "Team request is required");
                }

                Team team = teamRepository
                                .findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Team not found: "
                                                                + id));

                String oldTeamName = team.getName()
                                .trim();

                String newTeamName = request.getName()
                                .trim();

                if (newTeamName.isBlank()) {

                        throw new RuntimeException(
                                        "Team name is required");
                }

                teamRepository
                                .findByNameIgnoreCase(
                                                newTeamName)
                                .ifPresent(existing -> {

                                        if (!existing
                                                        .getId()
                                                        .equals(id)) {

                                                throw new RuntimeException(
                                                                "Team name already exists");
                                        }
                                });

                Integer oldStatus = team.getStatus();

                Integer newStatus = request.getStatus() == null
                                ? oldStatus
                                : request.getStatus();

                validateStatus(
                                newStatus);

                List<User> users = userRepository
                                .findByTeamIgnoreCase(
                                                oldTeamName);

                team.setName(
                                newTeamName);

                team.setStatus(
                                newStatus);

                team.setUpdatedBy(
                                updaterEmpID);

                if (newStatus == 0) {

                        team.setDeletedAt(
                                        LocalDateTime.now());

                        team.setDeletedBy(
                                        updaterEmpID);

                } else {

                        team.setDeletedAt(null);

                        team.setDeletedBy(null);
                }

                Team savedTeam = teamRepository.save(team);

                if (!oldTeamName.equalsIgnoreCase(
                                newTeamName)) {

                        for (User user : users) {

                                user.setTeam(
                                                newTeamName);

                                user.setUpdatedAt(
                                                LocalDateTime.now());

                                user.setUpdatedBy(
                                                updaterEmpID);
                        }

                        userRepository.saveAll(
                                        users);
                }

                return mapToResponse(
                                savedTeam);
        }

        @Transactional
        @Override
        public void deleteTeam(
                        Long id,
                        String deletedBy) {

                if (deletedBy == null
                                || deletedBy.isBlank()) {

                        throw new RuntimeException(
                                        "Deleted by employee ID is required");
                }

                Team team = teamRepository
                                .findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Team not found: "
                                                                + id));

                if (team.getStatus() != null
                                && team.getStatus() == 0) {

                        throw new RuntimeException(
                                        "Team is already inactive");
                }

                team.setStatus(0);

                team.setDeletedAt(
                                LocalDateTime.now());

                team.setDeletedBy(
                                deletedBy);

                team.setUpdatedBy(
                                deletedBy);

                teamRepository.save(
                                team);
        }

        private void validateStatus(
                        Integer status) {

                if (status == null
                                || (status != 0
                                                && status != 1)) {

                        throw new RuntimeException(
                                        "Status must be 1 (Active) or 0 (Inactive)");
                }
        }

        private TeamResponse mapToResponse(
                        Team team) {

                return new TeamResponse(

                                team.getId(),

                                team.getName(),

                                team.getStatus(),

                                team.getCreatedAt(),

                                team.getUpdatedAt(),

                                team.getCreatedBy(),

                                team.getUpdatedBy(),

                                team.getDeletedAt(),

                                team.getDeletedBy());
        }
}