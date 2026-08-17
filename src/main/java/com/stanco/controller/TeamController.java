package com.stanco.controller;

import com.stanco.dto.request.CreateTeamRequest;
import com.stanco.dto.request.UpdateTeamRequest;
import com.stanco.dto.response.TeamResponse;

import com.stanco.service.TeamService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
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
    public ResponseEntity<TeamResponse>
    createTeam(

            @Valid
            @RequestBody
            CreateTeamRequest request,

            Authentication authentication
    ) {


        String creatorEmpID =
                authentication.getName();


        TeamResponse response =
                teamService.createTeam(
                        request,
                        creatorEmpID
                );


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    

    @GetMapping
    public ResponseEntity<List<TeamResponse>>
    getAllTeams() {


        return ResponseEntity.ok(
                teamService.getAllTeams()
        );
    }


    

    @GetMapping("/active")
    public ResponseEntity<List<TeamResponse>>
    getActiveTeams() {


        return ResponseEntity.ok(
                teamService.getActiveTeams()
        );
    }


    

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponse>
    getTeamById(
            @PathVariable Long id
    ) {


        return ResponseEntity.ok(
                teamService.getTeamById(
                        id
                )
        );
    }


   

    @PutMapping("/{id}")
    public ResponseEntity<TeamResponse>
    updateTeam(

            @PathVariable Long id,

            @Valid
            @RequestBody
            UpdateTeamRequest request,

            Authentication authentication
    ) {


        String updaterEmpID =
                authentication.getName();


        TeamResponse response =
                teamService.updateTeam(
                        id,
                        request,
                        updaterEmpID
                );


        return ResponseEntity.ok(
                response
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteTeam(
            @PathVariable Long id
    ) {


        teamService.deleteTeam(
                id
        );


        return ResponseEntity
                .noContent()
                .build();
    }
}