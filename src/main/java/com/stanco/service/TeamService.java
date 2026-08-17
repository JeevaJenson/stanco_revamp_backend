package com.stanco.service;

import com.stanco.dto.request.CreateTeamRequest;
import com.stanco.dto.request.UpdateTeamRequest;
import com.stanco.dto.response.TeamResponse;

import java.util.List;

public interface TeamService {


    

    TeamResponse createTeam(
            CreateTeamRequest request,
            String creatorEmpID
    );


    
    List<TeamResponse> getAllTeams();



    List<TeamResponse> getActiveTeams();


   

    TeamResponse getTeamById(
            Long id
    );


   
    TeamResponse updateTeam(
            Long id,
            UpdateTeamRequest request,
            String updaterEmpID
    );


    

    void deleteTeam(
            Long id
    );
}