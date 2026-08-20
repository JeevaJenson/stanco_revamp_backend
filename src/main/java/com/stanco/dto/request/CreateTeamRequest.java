package com.stanco.dto.request;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CreateTeamRequest {

    @NotBlank(message = "Team name is required")
    private String name;

    private Integer status;
}