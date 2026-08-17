package com.stanco.dto.response;

import com.stanco.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class VerticalResponse {

    private Long id;

    private String verticalName;

    private Status status;

    private String createdBy;

    private String updatedBy;

    private String deletedBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;
}