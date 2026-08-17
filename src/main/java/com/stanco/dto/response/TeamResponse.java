package com.stanco.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TeamResponse {

    private Long id;

    private String name;

   
    private Integer status;

   
    private LocalDateTime createdAt;

   
    private LocalDateTime updatedAt;

   
    private String createdBy;

    
    private String updatedBy;
}