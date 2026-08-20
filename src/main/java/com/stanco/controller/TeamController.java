package com.stanco.controller;

import com.stanco.dto.request.CreateTeamRequest;
import com.stanco.dto.request.UpdateTeamRequest;
import com.stanco.dto.response.TeamResponse;

import com.stanco.service.TeamService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

        private final TeamService teamService;

        @PostMapping
        public ResponseEntity<TeamResponse> createTeam(

                        @Valid @RequestBody CreateTeamRequest request,

                        Authentication authentication

        ) {

                String creatorEmpID = authentication.getName();

                return ResponseEntity.ok(
                                teamService.createTeam(
                                                request,
                                                creatorEmpID));
        }

        @GetMapping
        public ResponseEntity<List<TeamResponse>> getAllTeams() {

                return ResponseEntity.ok(
                                teamService.getAllTeams());
        }

        @GetMapping("/active")
        public ResponseEntity<List<TeamResponse>> getActiveTeams() {

                return ResponseEntity.ok(
                                teamService.getActiveTeams());
        }

        @GetMapping("/{id}")
        public ResponseEntity<TeamResponse> getTeamById(
                        @PathVariable Long id) {

                return ResponseEntity.ok(
                                teamService.getTeamById(id));
        }

        @PutMapping("/{id}")
        public ResponseEntity<TeamResponse> updateTeam(

                        @PathVariable Long id,

                        @Valid @RequestBody UpdateTeamRequest request,

                        Authentication authentication

        ) {

                String updaterEmpID = authentication.getName();

                return ResponseEntity.ok(
                                teamService.updateTeam(
                                                id,
                                                request,
                                                updaterEmpID));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteTeam(

                        @PathVariable Long id,

                        Authentication authentication

        ) {

                String deletedBy = authentication.getName();

                teamService.deleteTeam(
                                id,
                                deletedBy);

                return ResponseEntity.noContent()
                                .build();
        }
}